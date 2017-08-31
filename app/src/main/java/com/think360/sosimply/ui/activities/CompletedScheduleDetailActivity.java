package com.think360.sosimply.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.think360.sosimply.AppController;
import com.think360.sosimply.R;
import com.think360.sosimply.manager.ApiService;
import com.think360.sosimply.model.schedule.detail.Data;
import com.think360.sosimply.model.schedule.detail.ScheduleDetailResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CompletedScheduleDetailActivity extends BaseActivity {

    @Inject
    ApiService apiService;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvTime)
    TextView tvTime;

    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.tvZone)
    TextView tvZone;

    @BindView(R.id.scheduleInfo)
    TextView scheduleInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_schedule_detail);

        ((AppController) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Completed Schedules");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (getIntent() != null) {
            if (getIntent().getParcelableExtra("SCHEDULE") instanceof com.think360.sosimply.model.schedule.detail.Data) {
                Data data = getIntent().getParcelableExtra("SCHEDULE");
                apiService.getSchduleDetails(data.getSchduleId()).enqueue(new Callback<ScheduleDetailResponse>() {
                    @Override
                    public void onResponse(Call<ScheduleDetailResponse> call, Response<ScheduleDetailResponse> response) {
                        if (response.body().getStatus() && response.isSuccessful()) {
                            tvTime.setText(response.body().getData().getTimeFrom() + " - " + response.body().getData().getTimeTo());
                            tvDate.setText(response.body().getData().getSchduleDate());
                            scheduleInfo.setText(response.body().getData().getSchduleInfo());
                        }
                    }

                    @Override
                    public void onFailure(Call<ScheduleDetailResponse> call, Throwable t) {

                    }
                });
            }
            if (getIntent().getParcelableExtra("SCHEDULE") instanceof com.think360.sosimply.model.schedule.Datum) {

                com.think360.sosimply.model.schedule.Datum data = getIntent().getParcelableExtra("SCHEDULE");
                apiService.getSchduleDetails(data.getSchduleId()).enqueue(new Callback<ScheduleDetailResponse>() {
                    @Override
                    public void onResponse(Call<ScheduleDetailResponse> call, Response<ScheduleDetailResponse> response) {
                        if (response.body().getStatus() && response.isSuccessful()) {
                            tvTime.setText(response.body().getData().getTimeFrom() + " - " + response.body().getData().getTimeTo());
                            tvDate.setText(response.body().getData().getSchduleDate());
                            scheduleInfo.setText(response.body().getData().getSchduleInfo());
                        }
                    }

                    @Override
                    public void onFailure(Call<ScheduleDetailResponse> call, Throwable t) {

                    }
                });
            }

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
