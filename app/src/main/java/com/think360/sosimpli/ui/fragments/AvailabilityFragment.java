package com.think360.sosimpli.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.adapters.HeaderAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;
import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.StickyHeaderAdapter;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.adapter_items.SimpleItem;
import com.think360.sosimpli.model.getavailibility.GetAvaliabilityResponse;
import com.think360.sosimpli.ui.activities.AddAvailabilityActivity;
import com.think360.sosimpli.ui.activities.AssignedScheduleDeatilActivity;
import com.think360.sosimpli.ui.activities.ChangeScheduleActivity;
import com.think360.sosimpli.ui.activities.NonApprovedActivity;
import com.think360.sosimpli.utils.AddAvailbailtyChanged;
import com.think360.sosimpli.utils.AppConstants;
import com.think360.sosimpli.widgets.DividerItemDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailabilityFragment extends Fragment {
    private static String startTime, endTime;
    private static Calendar startTimeCalendar, endTimeCalendar;

    private static final String[] headers = new String[]{"Thur, 11 July 2017", "Wed, 12 July 2017", "Fri, 13 July 2017", "Sat, 14 July 2017", "A, 15 July 2017", "K,16 July 2017", "L,17 July 2017", "M,18 July 2017"};
    //save our FastAdapter
    private FastItemAdapter fastItemAdapter;

    private FooterAdapter<ProgressItem> footerAdapter;
    private TextView tvAddAvailability, tvApprovedShifts, tvNonApprovedShifts;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SwipeRefreshLayout swipeLayout;
    private OnFragmentInteractionListener mListener;
    private HeaderAdapter headerAdapter;
    @Inject
    ApiService apiService;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.availability_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppController) getActivity().getApplication()).getComponent().inject(AvailabilityFragment.this);
        //  findViewById(android.R.id.content).setSystemUiVisibility(findViewById(android.R.id.content).getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //  super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_home);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);

        tvAddAvailability = (TextView) view.findViewById(R.id.tvAddAvailability);

        tvApprovedShifts = (TextView) view.findViewById(R.id.tvApprovedShifts);
        tvNonApprovedShifts = (TextView) view.findViewById(R.id.tvNonApprovedShifts);
        //create our FastAdapter
        fastItemAdapter = new FastItemAdapter();
        fastItemAdapter.withSelectable(true);

        //create our adapters
        final StickyHeaderAdapter stickyHeaderAdapter = new StickyHeaderAdapter();
        headerAdapter = new HeaderAdapter();


        compositeDisposable.add(((AppController) getActivity().getApplication()).bus().toObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                if (o instanceof AddAvailbailtyChanged) {
                    fetchDataFromRemote();
                }

            }
        }));


        //configure our fastAdapter
        //as we provide id's for the items we want the hasStableIds enabled to speed up things
        fastItemAdapter.setHasStableIds(true);

        //create our FooterAdapter which will manage the progress items
        footerAdapter = new FooterAdapter<>();

        //get our recyclerView and do basic setup
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.recyclerview_divider));
        rv.setAdapter(stickyHeaderAdapter.wrap(headerAdapter.wrap(footerAdapter.wrap(fastItemAdapter))));


        //this adds the Sticky Headers within our list
        final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(stickyHeaderAdapter);
        rv.addItemDecoration(decoration);

        swipeLayout.setRefreshing(true);
        fetchDataFromRemote();

        //fill with some sample data
        // headerAdapter.add(new SimpleItem().withName(headers[0]).withIdentifier(1));
 /*       List<IItem> items1 = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            items1.add(new SimpleItem().withName("Test " + i).withHeader(headers[i]));
        }
        headerAdapter.add(items1);*/

        //so the headers are aware of changes
        stickyHeaderAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                decoration.invalidateHeaders();
            }
        });

        //restore selections (this has to be done after the items were added
        fastItemAdapter.withSavedInstanceState(savedInstanceState);


        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(footerAdapter) {
            @Override
            public void onLoadMore(final int currentPage) {
            /*    footerAdapter.clear();
                footerAdapter.add(new ProgressItem().withEnabled(false));
                //simulate networking (2 seconds)
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        footerAdapter.clear();

                        //fill with some sample data
                        //  headerAdapter.add(new SimpleItem().withName("Header").withIdentifier(1));
                        List<IItem> items1 = new ArrayList<>();
                        for (int i = 0; i <= 6; i++) {
                            items1.add(new SimpleItem().withName("Test " + i).withHeader(headers[i]).withIdentifier(i));
                        }
                        headerAdapter.add(items1);
                        //stickyHeaderAdapter.notifyDataSetChanged();


                    }
                }, 2000);*/
            }
        });


        // We're doing nothing with this listener here this time. Check example usage
        // from ThreeTabsActivity on how to use it.
      /*  bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

            }
        });*/

        fastItemAdapter.withOnClickListener(new FastAdapter.OnClickListener<SimpleItem>() {
            @Override
            public boolean onClick(View v, IAdapter<SimpleItem> adapter, SimpleItem item, int position) {

                if (item.availabilityStatus) {
                    startActivity(new Intent(getActivity(), AssignedScheduleDeatilActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), ChangeScheduleActivity.class));
                }


                return true;
            }
        });
        tvAddAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddAvailabilityActivity.class));
                getActivity().overridePendingTransition(R.anim.zoom_exit, 0);
            }
        });
        tvApprovedShifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(Uri.parse("http://www.google.com"));
            }
        });

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchDataFromRemote();
            }
        });

        tvNonApprovedShifts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NonApprovedActivity.class));
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
                //getActivity().overridePendingTransition(R.anim.zoom_exit, 0);
            }
        });
    }

    private void fetchDataFromRemote() {
        swipeLayout.setRefreshing(true);
        apiService.getAvailability(AppController.sharedPreferencesCompat.getInt(AppConstants.DRIVER_ID, 0)).enqueue(new Callback<GetAvaliabilityResponse>() {
            @Override
            public void onResponse(Call<GetAvaliabilityResponse> call, Response<GetAvaliabilityResponse> response) {
                if (response.isSuccessful() && response.body().getStatus()) {
                    swipeLayout.setRefreshing(false);
                    fastItemAdapter.clear();
                    footerAdapter.clear();
                    headerAdapter.clear();
                    List<IItem> items = new ArrayList<>();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        for (int j = 0; j < response.body().getData().get(i).getTime().size(); j++) {
                            items.add(new SimpleItem().withHeader(response.body().getData().get(i).getStartDate()).withTime(response.body().getData().get(i).getTime().get(j).getFromTime() + "-" + response.body().getData().get(i).getTime().get(j).getToTime()).withIdentifier(Integer.parseInt(response.body().getData().get(i).getTime().get(j).getAvalabilityId())).withAvalibalityStatus(response.body().getData().get(i).getTime().get(j).getAvalibalityStatus()));
                        }
                    }
                    headerAdapter.add(items);

                } else {
                    Log.d(AvailabilityFragment.class.getSimpleName(), response.body().getMessage());
                    swipeLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<GetAvaliabilityResponse> call, Throwable t) {
                Log.d(AvailabilityFragment.class.getSimpleName(), t.getMessage());
                swipeLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState = fastItemAdapter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
        //outState.putInt("someVarA", someVarA);
        //outState.putString("someVarB", someVarB);
    }
  /*  @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add_availability the values which need to be saved from the adapter to the bundel
        outState = fastItemAdapter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and zone
        void onFragmentInteraction(Uri uri);
    }


}
