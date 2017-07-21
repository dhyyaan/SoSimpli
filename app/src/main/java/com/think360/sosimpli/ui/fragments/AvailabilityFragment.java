package com.think360.sosimpli.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.adapters.HeaderAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;
import com.think360.sosimpli.R;
import com.think360.sosimpli.StickyHeaderAdapter;
import com.think360.sosimpli.model.adapter_items.SimpleItem;
import com.think360.sosimpli.ui.activities.AddAvailabilityActivity;
import com.think360.sosimpli.ui.activities.AssignedScheduleDeatilActivity;
import com.think360.sosimpli.ui.activities.LoginActivity;
import com.think360.sosimpli.ui.activities.SplashActivity;
import com.think360.sosimpli.widgets.DividerItemDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

public class AvailabilityFragment extends Fragment {


    private static final String[] headers = new String[]{"Wed, 12 July 2017", "Wed, 12 July 2017", "Wed, 12 July 2017", "Wed, 12 July 2017", "Wed, 12 July 2017", "Tue,13 July 2017", "Thus,14 July 2017", "Thus,15 July 2017"};
    //save our FastAdapter
    private FastItemAdapter fastItemAdapter;

    private FooterAdapter<ProgressItem> footerAdapter;
    private TextView tvAddAvailability;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.availability_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  findViewById(android.R.id.content).setSystemUiVisibility(findViewById(android.R.id.content).getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //  super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_home);


        tvAddAvailability = (TextView) view.findViewById(R.id.tvAddAvailability);


        //create our FastAdapter
        fastItemAdapter = new FastItemAdapter();
        fastItemAdapter.withSelectable(true);

        //create our adapters
        final StickyHeaderAdapter stickyHeaderAdapter = new StickyHeaderAdapter();
        final HeaderAdapter headerAdapter = new HeaderAdapter();
        final ItemAdapter itemAdapter = new ItemAdapter();


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


        //fill with some sample data
        // headerAdapter.add(new SimpleItem().withName(headers[0]).withIdentifier(1));
        List<IItem> items1 = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            items1.add(new SimpleItem().withName("Test " + i).withHeader(headers[0]).withIdentifier(i));
        }
        headerAdapter.add(items1);

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
                footerAdapter.clear();
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
                }, 2000);
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

                startActivity(new Intent(getActivity(), AssignedScheduleDeatilActivity.class));

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

}
