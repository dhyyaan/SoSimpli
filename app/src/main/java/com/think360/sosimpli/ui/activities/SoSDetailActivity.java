package com.think360.sosimpli.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.AcceptRejectSosResponse;
import com.think360.sosimpli.model.sos.Datum;
import com.think360.sosimpli.model.sos.detail.SoSDetailResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SoSDetailActivity extends BaseActivity {


    @Inject
    ApiService apiService;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvDecline)
    TextView tvDecline;

    @BindView(R.id.tvAccept)
    TextView tvAccept;


    @BindView(R.id.tvTime)
    TextView tvTime;

    @BindView(R.id.tvZone)
    TextView tvZone;
    @BindView(R.id.tvCity)
    TextView tvCity;

    @BindView(R.id.tvDetailContent)
    TextView tvDetailContent;

    private Datum datum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sos_detail);
        ((AppController) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SOS Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (getIntent() != null) {
            datum = getIntent().getParcelableExtra("SOS_ITEM");
            tvCity.setText(datum.getState());
            tvZone.setText(datum.getZones());
            tvTime.setText(datum.getSosTime());
            tvDetailContent.setText(datum.getText());

        }

        apiService.getSoSDetail(datum.getSosId()).enqueue(new Callback<SoSDetailResponse>() {
            @Override
            public void onResponse(Call<SoSDetailResponse> call, Response<SoSDetailResponse> response) {
                if (response.body().getStatus() && response.isSuccessful()) {

                    tvCity.setText(response.body().getData().getCity());
                    tvZone.setText(response.body().getData().getZones());
                    tvTime.setText(response.body().getData().getTimeFrom() + " - " + response.body().getData().getTimeTo());
                    tvDetailContent.setText(response.body().getData().getMessage());


                }
            }

            @Override
            public void onFailure(Call<SoSDetailResponse> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.tvDecline)
    void onClickDecline() {
        final Dialog dialog = new Dialog(SoSDetailActivity.this);
        dialog.setContentView(R.layout.dialog_send_request);
        ((ProgressBar) dialog.findViewById(R.id.progressbar)).setIndeterminate(true);
        (dialog.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
        ((TextView) dialog.findViewById(R.id.text)).setText("Sending Request..");
        dialog.show();
        apiService.acceptRejectSos(driverId, datum.getSosId(), 0).enqueue(new Callback<AcceptRejectSosResponse>() {
            @Override
            public void onResponse(Call<AcceptRejectSosResponse> call, Response<AcceptRejectSosResponse> response) {
                if (response.body().getStatus() && response.isSuccessful()) {
                    (dialog.findViewById(R.id.progressbar)).setVisibility(View.GONE);
                    (dialog.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                    ((TextView) dialog.findViewById(R.id.text)).setText(response.body().getMessage());
                    animatemy(dialog.findViewById(R.id.imageView));
                } else {
                    showDialog(response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<AcceptRejectSosResponse> call, Throwable t) {
                Toast.makeText(SoSDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.tvAccept)
    void onClickAccept() {
        final Dialog dialog = new Dialog(SoSDetailActivity.this);
        dialog.setContentView(R.layout.dialog_send_request);
        ((ProgressBar) dialog.findViewById(R.id.progressbar)).setIndeterminate(true);
        (dialog.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
        ((TextView) dialog.findViewById(R.id.text)).setText("Sending Request..");
        dialog.show();
        apiService.acceptRejectSos(driverId, datum.getSosId(), 1).enqueue(new Callback<AcceptRejectSosResponse>() {
            @Override
            public void onResponse(Call<AcceptRejectSosResponse> call, Response<AcceptRejectSosResponse> response) {
                if (response.body().getStatus() && response.isSuccessful()) {
                    (dialog.findViewById(R.id.progressbar)).setVisibility(View.GONE);
                    (dialog.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                    ((TextView) dialog.findViewById(R.id.text)).setText(response.body().getMessage());
                    animatemy(dialog.findViewById(R.id.imageView));
                } else {
                    showDialog(response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<AcceptRejectSosResponse> call, Throwable t) {
                Toast.makeText(SoSDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void showDialog(String text) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_send_request);
        dialog.show();
        ((TextView) dialog.findViewById(R.id.text)).setText(text);
        animateView(dialog.findViewById(R.id.imageView));
    }

    private void animateView(View view) {
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        myAnim.setInterpolator(new MyBounceInterpolator(0.15, 30));
        view.startAnimation(myAnim);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
