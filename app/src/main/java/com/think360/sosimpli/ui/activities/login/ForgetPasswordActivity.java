package com.think360.sosimpli.ui.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.hbb20.CountryCodeDialog;
import com.hbb20.CountryCodePicker;
import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.ForgetPasswordResponse;
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

public class ForgetPasswordActivity extends BaseActivity {

    @Inject
    ApiService apiService;

    @BindView(R.id.btnSignIn)
    TextView btnSignIn;

    @BindView(R.id.etEmail)
    TextView etEmail;

    @BindView(R.id.loginMainLayout)
    CoordinatorLayout loginMainLayout;


    @BindView(R.id.ccp)
    CountryCodePicker ccp;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ((AppController) getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forget Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @OnClick(R.id.btnSignIn)
    protected void onClickGetOtp() {
        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            Snackbar.make(loginMainLayout, "Please provide valid email or mobile number", Snackbar.LENGTH_SHORT).show();
            //  Toast.makeText(LoginActivity.this, "Email can't be empty", Toast.LENGTH_SHORT).show();

        } else {
            if (isValidMobile(etEmail.getText().toString().trim())) {

                CountryCodeDialog.openCountryCodeDialog(ccp);
                ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
                    @Override
                    public void onCountrySelected() {
                        callLoginApi(ccp.getSelectedCountryCodeWithPlus() + etEmail.getText().toString().trim());
                        Log.d("COUNTRY", ccp.getSelectedCountryCodeWithPlus());
                    }
                });

            } else if (KeyboardUtil.isValidEmail(etEmail.getText().toString().trim())) {
                callLoginApi(etEmail.getText().toString().trim());
            } else {
                Snackbar.make(loginMainLayout, "Please fill valid email or mobile number", Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    void callLoginApi(String mobile) {
        showProgressDialog(this);
        pDialog.show();
        apiService.forgetPassword(mobile).enqueue(new Callback<ForgetPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {

                if (response.body().getStatus() && response.isSuccessful()) {
                    if (response.body().getCode() == 2) {
                        if (pDialog != null)
                            pDialog.dismiss();
                        startActivity(new Intent(ForgetPasswordActivity.this, VerifyOtpActivity.class).putExtra(AppConstants.DRIVER_ID, response.body().getData().getId()));
                    }
                    if (response.body().getCode() == 3) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1000);
                        if (pDialog != null)
                            pDialog.dismiss();
                        Snackbar.make(loginMainLayout, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (pDialog != null)
                        pDialog.dismiss();
                    Snackbar.make(loginMainLayout, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {
                if (pDialog != null)
                    pDialog.dismiss();
                Snackbar.make(loginMainLayout, t.getMessage(), Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
