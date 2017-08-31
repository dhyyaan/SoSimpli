package com.think360.sosimply.ui.fragments;

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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.drag.SimpleDragCallback;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;
import com.think360.sosimply.AppController;
import com.think360.sosimply.R;
import com.think360.sosimply.manager.ApiService;
import com.think360.sosimply.model.adapter_items.SoSItem;
import com.think360.sosimply.model.sos.AllSoSResponse;
import com.think360.sosimply.model.sos.Datum;
import com.think360.sosimply.notification.EventToRefresh;
import com.think360.sosimply.ui.activities.SoSDetailActivity;
import com.think360.sosimply.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoSFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoSFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private OnFragmentInteractionListener mListener;


    @Inject
    ApiService apiService;

    //save our FastAdapter
    private FastItemAdapter<SoSItem> fastItemAdapter;
    private FooterAdapter<ProgressItem> footerAdapter;
    private SwipeRefreshLayout swipeLayout;
    //drag & drop
    private SimpleDragCallback touchCallback;
    private ItemTouchHelper touchHelper;
    private TextView tvNoData;
    public SoSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoSFragment newInstance(String param1, String param2) {
        SoSFragment fragment = new SoSFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avilability, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppController) getActivity().getApplication()).getComponent().inject(SoSFragment.this);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        //create our FastAdapter which will manage everything
        fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.withSelectable(true);
        tvNoData = (TextView) view.findViewById(R.id.tvNoData);
        //create our FooterAdapter which will manage the progress items
        footerAdapter = new FooterAdapter<>();

        //configure our fastAdapter
        fastItemAdapter.withOnClickListener(new FastAdapter.OnClickListener<SoSItem>() {
            @Override
            public boolean onClick(View v, IAdapter<SoSItem> adapter, SoSItem item, int position) {
                Toast.makeText(v.getContext(), (item).zone.getText(v.getContext()), Toast.LENGTH_LONG).show();
                return false;
            }
        });



        //get our recyclerView and do basic setup
        RecyclerView recyclerView = (RecyclerView)view. findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(footerAdapter.wrap(fastItemAdapter));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.recyclerview_divider));


        compositeDisposable.add(((AppController) getActivity().getApplication()).bus().toObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                if (o instanceof EventToRefresh) {
                    if (((EventToRefresh) o).getBody() == R.id.tab_friends1) {
                        fetchDataFromRemote();
                    }

                }

            }
        }));

        fetchDataFromRemote();

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(footerAdapter) {
            @Override
            public void onLoadMore(final int currentPage) {
              /*  footerAdapter.clear();
                footerAdapter.add(new ProgressItem().withEnabled(false));
                //simulate networking (2 seconds)
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        footerAdapter.clear();
                        for (int i = 1; i < 16; i++) {
                            fastItemAdapter.add(fastItemAdapter.getAdapterItemCount(), new SoSItem().withZone("Item " + i + " Page " + currentPage));
                        }
                    }
                }, 2000);*/
            }
        });

        /*//fill with some sample data (load the first page here)
        List<SoSItem> items = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            items.add(new SoSItem().withName("Item " + i + " Page " + 1));
        }
        fastItemAdapter.add(items);*/

        //restore selections (this has to be done after the items were added
        fastItemAdapter.withSavedInstanceState(savedInstanceState);

        fastItemAdapter.withOnClickListener(new FastAdapter.OnClickListener<SoSItem>() {
            @Override
            public boolean onClick(View v, IAdapter<SoSItem> adapter, SoSItem item, int position) {
                startActivity(new Intent(getActivity(), SoSDetailActivity.class).putExtra("SOS_ITEM", item.getDatum()));

                return true;
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDataFromRemote();
            }
        });
    }

    private void fetchDataFromRemote() {
        swipeLayout.setRefreshing(true);
        apiService.getAllSoS().enqueue(new Callback<AllSoSResponse>() {
            @Override
            public void onResponse(Call<AllSoSResponse> call, Response<AllSoSResponse> response) {
                swipeLayout.setRefreshing(false);
                if (response.isSuccessful() && response.body().getStatus()) {
                    fastItemAdapter.clear();
                    footerAdapter.clear();

                    tvNoData.setVisibility(View.GONE);
                    List<SoSItem> items = new ArrayList<>();
                    for (Datum datum : response.body().getData()) {
                        items.add(new SoSItem().setDatum(datum));
                        //items.add(new SoSItem().withZone(datum.getZones() + " | " + datum.getState()));
                    }
                    fastItemAdapter.add(items);


                } else {
                    fastItemAdapter.clear();
                    footerAdapter.clear();
                    tvNoData.setText("No SOS Available");
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AllSoSResponse> call, Throwable t) {
                swipeLayout.setRefreshing(false);
                tvNoData.setVisibility(View.VISIBLE);
                tvNoData.setText("Something went wrong!\n Refresh again");
            }
        });

    }


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
