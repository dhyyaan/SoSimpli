package com.think360.sosimpli.ui.activities.login;

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
import com.think360.sosimpli.model.WorkerEditProfileModel;
import com.think360.sosimpli.ui.activities.BaseActivity;
import com.think360.sosimpli.utils.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPasswordActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.loginMainLayout)
    CoordinatorLayout loginMainLayout;

    @Inject
    ApiService apiService;

    @BindView(R.id.btnSignIn)
    TextView btnSignIn;

    private String driverId;


    @BindView(R.id.etEmail)
    TextView etEmail;

    @BindView(R.id.confirmPassword)
    TextView confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        ((AppController) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
     //   getSupportActionBar().setTitle("Forget Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent() != null) {
            driverId = getIntent().getStringExtra(AppConstants.DRIVER_ID);
        }
    }

    @OnClick(R.id.btnSignIn)
    protected void OnClickConfirmPassowrd() {
        if (TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(confirmPassword.getText().toString()) || !etEmail.getText().toString().equals(confirmPassword.getText().toString())) {
            if (TextUtils.isEmpty(etEmail.getText().toString())) {
                Snackbar.make(loginMainLayout, "Password Cant be empty", Snackbar.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
                Snackbar.make(loginMainLayout, "Confirm Password Cant be empty", Snackbar.LENGTH_SHORT).show();


            } else {
                Snackbar.make(loginMainLayout, "Password does noty match", Snackbar.LENGTH_SHORT).show();


            }
        } else {
            showProgressDialog(this);
            pDialog.show();
            apiService.editDriverProfile(RequestBody.create(MediaType.parse("text/plain"), driverId), null, RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString().trim()), null).enqueue(new Callback<WorkerEditProfileModel>() {
                @Override
                public void onResponse(Call<WorkerEditProfileModel> call, Response<WorkerEditProfileModel> response) {
                    if (response.isSuccessful() && response.body().getStatus()) {
                        if (pDialog != null)
                            pDialog.dismiss();
                        startActivity(new Intent(ConfirmPasswordActivity.this, LoginActivity.class));
                        finish();

                    } else {
                        if (pDialog != null)
                            pDialog.dismiss();
                        Snackbar.make(loginMainLayout, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WorkerEditProfileModel> call, Throwable t) {
                    Snackbar.make(loginMainLayout, "Fill Otp", Snackbar.LENGTH_SHORT).show();

                }
            });
        }
    }
    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

}
