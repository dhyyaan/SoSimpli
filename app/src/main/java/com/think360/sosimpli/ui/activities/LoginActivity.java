package com.think360.sosimpli.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.think360.sosimpli.AppController;
import com.think360.sosimpli.R;
import com.think360.sosimpli.databinding.ActivityMainBinding;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.presenter.LoginPresenter;
import com.think360.sosimpli.utils.AppConstants;
import com.think360.sosimpli.utils.KeyboardUtil;

import javax.inject.Inject;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, View.OnClickListener {


    @Inject
    ApiService apiService;


    private ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppController) getApplication()).getComponent().inject(this);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.btnSignIn.setOnClickListener(this);
    }

    @Override
    public void loginSuccessful(String firstName, int workerId) {
        AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_REMEMBER_TAPPED, activityMainBinding.switchGprs.isChecked()).apply();
        AppController.getSharedPrefEditor().putBoolean(AppConstants.IS_LOGIN, true).apply();
        callMainActivity();
    }


    @Override
    public void loginFailed(Throwable t) {
        Snackbar.make(activityMainBinding.loginMainLayout, t.getStackTrace() + "", Snackbar.LENGTH_SHORT).show();

        Timber.d("FAILED", t.getStackTrace());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
               // callMainActivity() ;
                if (TextUtils.isEmpty(activityMainBinding.etEmail.getText().toString().trim()) || TextUtils.isEmpty(activityMainBinding.etPassword.getText().toString().trim()) | !KeyboardUtil.isValidEmail(activityMainBinding.etEmail.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(activityMainBinding.etEmail.getText().toString().trim())) {
                        Toast.makeText(LoginActivity.this, "Email can't be empty", Toast.LENGTH_SHORT).show();

                    } else if (!KeyboardUtil.isValidEmail(activityMainBinding.etEmail.getText().toString().trim())) {
                        Snackbar.make(activityMainBinding.loginMainLayout,"Email should be valid",Snackbar.LENGTH_SHORT).show();
                      //  Toast.makeText(LoginActivity.this, "Email should be valid", Toast.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(activityMainBinding.loginMainLayout,"Password can't be empty",Snackbar.LENGTH_SHORT).show();

                       // Toast.makeText(LoginActivity.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    new LoginPresenter(this, apiService, activityMainBinding.etEmail.getText().toString().trim(), activityMainBinding.etPassword.getText().toString().trim());
                }
                break;
        }
    }

    private void callMainActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
        overridePendingTransition(R.anim.zoom_exit, 0);
    }

}
