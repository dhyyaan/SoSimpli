package com.think360.sosimpli.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.adapter_items.CompletedScheduleItem;
import com.think360.sosimpli.model.schedule.CompletedScheduleResponse;
import com.think360.sosimpli.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CompletedSchedulesActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    //save our FastAdapter
    private FastItemAdapter<CompletedScheduleItem> fastItemAdapter;
    private FooterAdapter<ProgressItem> footerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppController) getApplication()).getComponent().inject(this);

        setContentView(R.layout.activity_completed_schedules);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Completed Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //create our FastAdapter which will manage everything
        fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.withSelectable(true);

        //create our FooterAdapter which will manage the progress items
        footerAdapter = new FooterAdapter<>();

        //configure our fastAdapter
        fastItemAdapter.withOnClickListener(new FastAdapter.OnClickListener<CompletedScheduleItem>() {
            @Override
            public boolean onClick(View v, IAdapter<CompletedScheduleItem> adapter, CompletedScheduleItem item, int position) {
                Toast.makeText(v.getContext(), (item).name.getText(v.getContext()), Toast.LENGTH_LONG).show();
                return false;
            }
        });


        //get our recyclerView and do basic setup
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(footerAdapter.wrap(fastItemAdapter));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.recyclerview_divider));
        swipeLayout.setRefreshing(true);
        apiService.getCompleteSchdule(driverId).enqueue(new Callback<CompletedScheduleResponse>() {
            @Override
            public void onResponse(Call<CompletedScheduleResponse> call, Response<CompletedScheduleResponse> response) {
                swipeLayout.setRefreshing(false);
                if (response.isSuccessful() && response.body().getStatus()) {
                    //fill with some sample data (load the first page here)
                    List<CompletedScheduleItem> items = new ArrayList<>();
                    for (int i = 1; i < response.body().getData().size(); i++) {
                        items.add(new CompletedScheduleItem().setDatum(response.body().getData().get(i)));
                    }
                    fastItemAdapter.add(items);
                }
            }

            @Override
            public void onFailure(Call<CompletedScheduleResponse> call, Throwable t) {
                swipeLayout.setRefreshing(false);
            }
        });

        /*recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(footerAdapter) {
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
                        for (int i = 1; i < 16; i++) {
                            fastItemAdapter.add(fastItemAdapter.getAdapterItemCount(), new CompletedScheduleItem().withName("Item " + i + " Page " + currentPage));
                        }
                    }
                }, 2000);
            }
        });*/


        //restore selections (this has to be done after the items were added
        fastItemAdapter.withSavedInstanceState(savedInstanceState);

        fastItemAdapter.withOnClickListener(new FastAdapter.OnClickListener<CompletedScheduleItem>() {
            @Override
            public boolean onClick(View v, IAdapter<CompletedScheduleItem> adapter, CompletedScheduleItem item, int position) {

                startActivity(new Intent(CompletedSchedulesActivity.this, CompletedScheduleDetailActivity.class).putExtra("SCHEDULE", item.getDatum()));

                return true;
            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
