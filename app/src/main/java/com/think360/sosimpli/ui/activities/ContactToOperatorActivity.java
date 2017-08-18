package com.think360.sosimpli.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.country.Country;
import com.think360.sosimpli.model.schedule.pending.Datum;
import com.think360.sosimpli.model.schedule.pending.PedningScheduleResponse;
import com.think360.sosimpli.model.trip.TripFinishStartResponse;
import com.think360.sosimpli.ui.fragments.CountriesDialogFragment;

import java.util.AbstractList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ContactToOperatorActivity extends BaseActivity implements CountriesDialogFragment.CallBack {
    @Inject
    ApiService apiService;

    @BindView(R.id.tvSendRequest)
    TextView tvSendRequest;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.etDescription)
    EditText etDescription;

    @BindView(R.id.etSubject)
    EditText etSubject;

    @BindView(R.id.etCountry)
    EditText etCountry;

    @BindView(R.id.progressBarCountry)
    ProgressBar progressBarCountry;

    private CountriesDialogFragment countriesDialogFragment;
    private String country_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppController) getApplication()).getComponent().inject(this);
        setContentView(R.layout.contact_to_operator);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact To Operator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBarCountry.setIndeterminate(true);
        progressBarCountry.setVisibility(View.VISIBLE);
        etCountry.setClickable(false);

        apiService.getPendingSchedule(driverId).enqueue(new Callback<PedningScheduleResponse>() {
            @Override
            public void onResponse(Call<PedningScheduleResponse> call, Response<PedningScheduleResponse> response) {
                if (response.body().getStatus() && response.isSuccessful()) {
                    progressBarCountry.setVisibility(View.GONE);
                    etCountry.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
                    etCountry.setClickable(true);
                    countriesDialogFragment = CountriesDialogFragment.newInstance();
                    countriesDialogFragment.setList((AbstractList) response.body().getData(), ContactToOperatorActivity.this);
                    etCountry.setClickable(true);
                } else {
                    showSimpleDialog(ContactToOperatorActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<PedningScheduleResponse> call, Throwable t) {

            }
        });


    }

    @OnClick(R.id.tvSendRequest)
    public void onClickSendRequest() {
        if (TextUtils.isEmpty(country_id) || TextUtils.isEmpty(etSubject.getText().toString()) || TextUtils.isEmpty(etSubject.getText().toString())) {
            Toast.makeText(ContactToOperatorActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        } else {


            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_send_request);
            ((ProgressBar) dialog.findViewById(R.id.progressbar)).setIndeterminate(true);
            (dialog.findViewById(R.id.progressbar)).setVisibility(View.VISIBLE);
            ((TextView) dialog.findViewById(R.id.text)).setText("Sending Request..");
            dialog.show();

            apiService.contactToOperator(driverId, "1", etSubject.getText().toString(), etDescription.getText().toString()).enqueue(new Callback<TripFinishStartResponse>() {
                @Override
                public void onResponse(Call<TripFinishStartResponse> call, Response<TripFinishStartResponse> response) {
                    dialog.dismiss();
                    if (response.body().getStatus()) {
                        (dialog.findViewById(R.id.progressbar)).setVisibility(View.GONE);
                        (dialog.findViewById(R.id.imageView)).setVisibility(View.VISIBLE);
                        ((TextView) dialog.findViewById(R.id.text)).setText(response.body().getMessage());
                        animatemy(dialog.findViewById(R.id.imageView));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBackPressed();
                            }
                        }, 100);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(ContactToOperatorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<TripFinishStartResponse> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /*
        class MyBounceInterpolator implements android.view.animation.Interpolator {
            private double mAmplitude = 5;
            private double mFrequency = 10;

            MyBounceInterpolator(double amplitude, double frequency) {
                mAmplitude = amplitude;
                mFrequency = frequency;
            }

            public float getInterpolation(float time) {
                return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                        Math.cos(mFrequency * time) + 1);
            }
        }*/
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void callBackMethod(Object object) {
        if (object instanceof Datum) {
            etCountry.setText(((Country) object).getCountryName());
            country_id = ((Country) object).getId();
        }
    }
}
