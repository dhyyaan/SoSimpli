package com.think360.sosimply.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.think360.sosimply.AppConstants;
import com.think360.sosimply.AppController;
import com.think360.sosimply.R;
import com.think360.sosimply.manager.ApiService;
import com.think360.sosimply.model.approved.Data;
import com.think360.sosimply.model.getavailibility.Time;
import com.think360.sosimply.model.trip.TripFinishStartResponse;
import com.think360.sosimply.notification.EventNotification;
import com.think360.sosimply.ui.fragments.AvailabilityFragment;

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

    @BindView(R.id.tvTimeForTrip)
    TextView tvTimeForTrip;
    @BindView(R.id.layoutStatus)
    LinearLayout layoutStatus;
    @BindView(R.id.tvScheduleTime)
    TextView tvScheduleTime;
    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvZone)
    TextView tvZone;
    @BindView(R.id.tvBookingId)
    TextView tvBookingId;
    @BindView(R.id.contactFormLayout)
    LinearLayout contactFormLayout;
    @BindView(R.id.tvInfo)
    TextView tvInfo;
    @BindView(R.id.phoneNumberOperator)
    TextView phoneNumberOperator;

    private Handler handler;
    private Runnable runnable;
    private int seconds;
    private boolean isTimeRunning = false;

    private Time time;

    private String avalability_id, tripid;

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

        phoneNumberOperator.setText(AppController.sharedPreferencesCompat.getString(AppConstants.OPERATOR_PHONE, "+1"));
        if (getIntent() != null) {
            if (getIntent().getParcelableExtra("TIME") instanceof Time) {
                time = getIntent().getParcelableExtra("TIME");
                tvScheduleTime.setText(time.getSchdule_TimeFrom() + " - " + time.getSchdule_TimeTo());
                tvCity.setText(time.schdule_city);
                tvZone.setText(time.availabiliy_zones + "," + time.getSchdule_city());
                tvBookingId.setText("Booking ID:" + time.randonno);
                tvInfo.setText(time.getSchdule_info());
                tripid = time.getTripid();
                avalability_id = time.getAvalability_id();
            }
            if (getIntent().getParcelableExtra("TIME") instanceof Data) {
                Data time = getIntent().getParcelableExtra("TIME");
                tvScheduleTime.setText(time.getSchdule_TimeFrom() + " - " + time.getSchdule_TimeTo());
                tvCity.setText(time.getCityname());
                tvZone.setText(time.getZone() + "," + time.getCityname());
                tvBookingId.setText("Booking ID:" + time.getRandonno());
                tvInfo.setText(time.getSchdule_info());
                tripid = time.getTrip_id();
                avalability_id = time.getAvailability_id();
            }


            switch (getIntent().getIntExtra("STATUS", 5)) {
                case AvailabilityFragment.State.ASSIGNED:
                    layoutStatus.setVisibility(View.GONE);
                    tvCheckIn.setText("CHECK IN");
                    isTimeRunning = false;
                    break;

                case AvailabilityFragment.State.RUNNING:
                    layoutStatus.setVisibility(View.VISIBLE);
                    tvCheckIn.setText("CHECK OUT");
                    contactFormLayout.setVisibility(View.GONE);
                    isTimeRunning = true;
                    break;

                default:
                    return;
            }
        }
    }

    @OnClick(R.id.tvCancel)
    public void onClickCancel() {
        startActivity(new Intent(this, ContactToOperatorActivity.class));
    }

    @OnClick(R.id.tvCheckIn)
    public void onClickCheckIn() {
        if (isTimeRunning) {
            onClickCheckOut();
        } else {
            checkIn();
        }

    }

    public void checkIn() {
        final Dialog dialog = new Dialog(AssignedScheduleDeatilActivity.this);
        dialog.setContentView(R.layout.dialog_send_request);
        ((ProgressBar) dialog.findViewById(R.id.progressbar)).setIndeterminate(true);
        (dialog.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
        ((TextView) dialog.findViewById(R.id.text)).setText("Sending Request..");
        dialog.show();


        apiService.tripStart(driverId, System.currentTimeMillis() / 1000L + "", avalability_id).enqueue(new Callback<TripFinishStartResponse>() {
            @Override
            public void onResponse(Call<TripFinishStartResponse> call, Response<TripFinishStartResponse> response) {

                if (response.body().getStatus()) {
                    ((AppController) getApplication()).bus().send(new EventNotification(""));
                    (dialog.findViewById(R.id.progressbar)).setVisibility(View.GONE);
                    (dialog.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                    ((TextView) dialog.findViewById(R.id.text)).setText(response.body().getMessage());
                    animatemy(dialog.findViewById(R.id.imageView));
                    layoutStatus.setVisibility(View.VISIBLE);
                    tvCheckIn.setText("CHECK OUT");
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            onBackPressed();

                        }
                    });

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

    public void onClickCheckOut() {

        final Dialog dialog = new Dialog(AssignedScheduleDeatilActivity.this);
        dialog.setContentView(R.layout.dialog_send_request);
        ((ProgressBar) dialog.findViewById(R.id.progressbar)).setIndeterminate(true);
        (dialog.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
        ((TextView) dialog.findViewById(R.id.text)).setText("Sending Request..");
        dialog.show();

        apiService.tripFinish(driverId, System.currentTimeMillis() / 1000L + "", avalability_id, tripid).enqueue(new Callback<TripFinishStartResponse>() {
            @Override
            public void onResponse(Call<TripFinishStartResponse> call, Response<TripFinishStartResponse> response) {
                if (response.isSuccessful())
                    if (response.body().getStatus()) {
                        ((AppController) getApplication()).bus().send(new EventNotification(""));
                        (dialog.findViewById(R.id.progressbar)).setVisibility(View.GONE);
                        (dialog.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                        ((TextView) dialog.findViewById(R.id.text)).setText(response.body().getMessage());
                        animatemy(dialog.findViewById(R.id.imageView));
                        layoutStatus.setVisibility(View.GONE);
                        tvCheckIn.setText("CHECK IN");
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                onBackPressed();

                            }
                        });
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
