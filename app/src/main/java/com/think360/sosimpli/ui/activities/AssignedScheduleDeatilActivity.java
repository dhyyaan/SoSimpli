package com.think360.sosimpli.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.think360.sosimpli.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AssignedScheduleDeatilActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvCancel)
    protected TextView tvCancel;

    @BindView(R.id.tvTime)
    protected TextView tvTime;

    @BindView(R.id.tvCheckIn)
    protected TextView tvCheckIn;

    private Handler handler;
    private Runnable runnable;
    private int seconds;
    private boolean isTimeRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assigned_schedule_details);
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
            tvTime.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.rectanglefilled_green));
            seconds = 0;
            tvCheckIn.setText("CHECK OUT");
            runTimer(tvTime);
        }

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
