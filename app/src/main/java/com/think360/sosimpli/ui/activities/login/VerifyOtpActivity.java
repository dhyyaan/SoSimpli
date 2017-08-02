package com.think360.sosimpli.ui.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.ResendOtpResponse;
import com.think360.sosimpli.model.VerifyOtpResponse;
import com.think360.sosimpli.ui.activities.BaseActivity;
import com.think360.sosimpli.utils.AppConstants;
import com.think360.sosimpli.utils.KeyboardUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VerifyOtpActivity extends BaseActivity {

    @BindView(R.id.loginMainLayout)
    CoordinatorLayout loginMainLayout;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    ApiService apiService;

    @BindView(R.id.btnConfirmOtp)
    TextView btnConfirmOtp;

    private String driverId;


    @BindView(R.id.etEmail)
    TextView etEmail;


    @BindView(R.id.btnResendOtp)
    TextView btnResendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ((AppController) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forget Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent() != null) {
            driverId = getIntent().getStringExtra(AppConstants.DRIVER_ID);
        }

    }


    @OnClick(R.id.btnResendOtp)
    protected void OnClickResend() {
        showProgressDialog(this);
        pDialog.show();
        apiService.resendOtp(driverId).enqueue(new Callback<ResendOtpResponse>() {
            @Override
            public void onResponse(Call<ResendOtpResponse> call, Response<ResendOtpResponse> response) {
                if (response.isSuccessful() && response.body().getStatus()) {
                    if (pDialog != null)
                        pDialog.dismiss();
                    KeyboardUtil.hideKeyboard(etEmail, VerifyOtpActivity.this);
                    Snackbar.make(loginMainLayout, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                } else {
                    if (pDialog != null)
                        pDialog.dismiss();
                    Snackbar.make(loginMainLayout, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResendOtpResponse> call, Throwable t) {
                if (pDialog != null)
                    pDialog.dismiss();
                Snackbar.make(loginMainLayout, t.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnConfirmOtp)
    protected void OnClick() {
        KeyboardUtil.hideKeyboard(btnConfirmOtp, this);
        if (!TextUtils.isEmpty(etEmail.getText().toString())) {
            showProgressDialog(this);
            pDialog.show();
            apiService.verifyOtp(driverId, etEmail.getText().toString().trim()).enqueue(new Callback<VerifyOtpResponse>() {
                @Override
                public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                    if (response.isSuccessful() && response.body().getStatus()) {
                        if (pDialog != null)
                            pDialog.dismiss();
                        startActivity(new Intent(VerifyOtpActivity.this, ConfirmPasswordActivity.class).putExtra(AppConstants.DRIVER_ID, response.body().getData().getId()));

                    } else {
                        if (pDialog != null)
                            pDialog.dismiss();
                        Snackbar.make(loginMainLayout, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                    if (pDialog != null)
                        pDialog.dismiss();
                    Snackbar.make(loginMainLayout, t.getMessage(), Snackbar.LENGTH_SHORT).show();

                }
            });
        } else {
            Snackbar.make(loginMainLayout, "Fill Otp", Snackbar.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }


}
