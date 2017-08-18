package com.think360.sosimpli.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.trip.TripFinishStartResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AssignedScheduleDeatilActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvCancel)
    protected TextView tvCancel;

    @BindView(R.id.tvTime)
    protected TextView tvTime;

    @BindView(R.id.tvCheckIn)
    protected TextView tvCheckIn;


    @BindView(R.id.tvCheckOut)
    protected TextView tvCheckOut;

    private Handler handler;
    private Runnable runnable;
    private int seconds;
    private boolean isTimeRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assigned_schedule_details);
        ((AppController) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assigned Schedule Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @OnClick(R.id.tvCancel)
    public void onClickCancel() {
        startActivity(new Intent(this, ContactToOperatorActivity.class));
    }

    @OnClick(R.id.tvCheckIn)
    public void onClickCheckIn() {
        if (isTimeRunning) {
            tvTime.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.rectangle_dark_grey_boundary));
            isTimeRunning = false;
            seconds = 0;
            tvCheckIn.setText("CHECK IN");
        } else {
            isTimeRunning = true;
            tvTime.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.rectanglefilled_green));
            seconds = 0;
            tvCheckIn.setText("CHECK OUT");
            runTimer(tvTime);


            final Dialog dialog = new Dialog(AssignedScheduleDeatilActivity.this);
            dialog.setContentView(R.layout.dialog_send_request);
            ((ProgressBar) dialog.findViewById(R.id.progressbar)).setIndeterminate(true);
            (dialog.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
            ((TextView) dialog.findViewById(R.id.text)).setText("Sending Request..");
            dialog.show();

            apiService.tripStart(driverId, "", "").enqueue(new Callback<TripFinishStartResponse>() {
                @Override
                public void onResponse(Call<TripFinishStartResponse> call, Response<TripFinishStartResponse> response) {

                    if (response.body().getStatus()) {
                        (dialog.findViewById(R.id.progressbar)).setVisibility(View.GONE);
                        (dialog.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                        ((TextView) dialog.findViewById(R.id.text)).setText(response.body().getMessage());
                        animatemy(dialog.findViewById(R.id.imageView));
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AssignedScheduleDeatilActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TripFinishStartResponse> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        }

    }

    @OnClick(R.id.tvCheckOut)
    public void onClickCheckOut() {

        final Dialog dialog = new Dialog(AssignedScheduleDeatilActivity.this);
        dialog.setContentView(R.layout.dialog_send_request);
        ((ProgressBar) dialog.findViewById(R.id.progressbar)).setIndeterminate(true);
        (dialog.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
        ((TextView) dialog.findViewById(R.id.text)).setText("Sending Request..");
        dialog.show();

        apiService.tripFinish(driverId, "", "", "").enqueue(new Callback<TripFinishStartResponse>() {
            @Override
            public void onResponse(Call<TripFinishStartResponse> call, Response<TripFinishStartResponse> response) {
                if (response.isSuccessful())
                    if (response.body().getStatus()) {
                        (dialog.findViewById(R.id.progressbar)).setVisibility(View.GONE);
                        (dialog.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                        ((TextView) dialog.findViewById(R.id.text)).setText(response.body().getMessage());
                        animatemy(dialog.findViewById(R.id.imageView));
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AssignedScheduleDeatilActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<TripFinishStartResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }


    public void runTimer(final TextView textView) {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, sec);
                textView.setText(time);
                isTimeRunning = true;
                if (isTimeRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);

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
