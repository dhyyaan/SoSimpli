package com.think360.sosimpli.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.adapters.HeaderAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.think360.sosimpli.R;
import com.think360.sosimpli.model.adapter_items.SimpleItem;
import com.think360.sosimpli.StickyHeaderAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String[] headers = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    //save our FastAdapter
    private FastItemAdapter fastItemAdapter;

    private FooterAdapter<ProgressItem> footerAdapter;
    private BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  findViewById(android.R.id.content).setSystemUiVisibility(findViewById(android.R.id.content).getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomBar = (BottomBar) findViewById(R.id.bottomBar);


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
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(stickyHeaderAdapter.wrap(headerAdapter.wrap(footerAdapter.wrap(fastItemAdapter))));


        //this adds the Sticky Headers within our list
        final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(stickyHeaderAdapter);
        rv.addItemDecoration(decoration);


        //fill with some sample data
        headerAdapter.add(new SimpleItem().withName("Header").withIdentifier(1));
        List<IItem> items1 = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            items1.add(new SimpleItem().withName("Test " + i).withHeader(headers[i / 5]).withIdentifier(100 + i));
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
                        headerAdapter.add(new SimpleItem().withName("Header").withIdentifier(1));
                        List<IItem> items1 = new ArrayList<>();
                        for (int i = 1; i <= 100; i++) {
                            items1.add(new SimpleItem().withName("Test " + i).withHeader(headers[i / 5]).withIdentifier(100 + i));
                        }
                        headerAdapter.add(items1);
                        //stickyHeaderAdapter.notifyDataSetChanged();


                    }
                }, 2000);
            }
        });


        // We're doing nothing with this listener here this time. Check example usage
        // from ThreeTabsActivity on how to use it.
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

            }
        });

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add_availability the values which need to be saved from the adapter to the bundel
        outState = fastItemAdapter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

}
