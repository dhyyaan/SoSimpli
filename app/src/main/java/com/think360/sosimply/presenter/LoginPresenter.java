package com.think360.sosimply.presenter;


import com.google.firebase.iid.FirebaseInstanceId;
import com.think360.sosimply.AppConstants;
import com.think360.sosimply.AppController;
import com.think360.sosimply.manager.ApiService;
import com.think360.sosimply.model.user.User;
import com.think360.sosimply.ui.activities.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by think360 on 18/04/17.
 */

public class LoginPresenter extends BasePresenter {

    private LoginPresenter.View view;

    public interface View {
        void loginSuccessful(String firstName, int workerId);
        void loginFailed(Throwable t);
    }

    public LoginPresenter(final LoginPresenter.View view, ApiService apiService, String email, String password) {
        super((LoginActivity) view);
        this.view = view;
        pDialog.show();
        apiService.loginUser(email, password, FirebaseInstanceId.getInstance().getToken(), "android").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body().getStatus()) {
                    pDialog.dismiss();

                    AppController.getSharedPrefEditor().putInt(AppConstants.DRIVER_ID, response.body().getData().getDriverId()).apply();
                    AppController.getSharedPrefEditor().putString(AppConstants.DRIVER_NAME, response.body().getData().getDriverName()).apply();
                    AppController.getSharedPrefEditor().putString(AppConstants.DRIVER_EMAIL, response.body().getData().getDriverEmail()).apply();
                    AppController.getSharedPrefEditor().putString(AppConstants.OPERATOR_PHONE, response.body().getData().getOperator_phone()).apply();
                    view.loginSuccessful(response.body().getData().getDriverName(), response.body().getData().getDriverId());

                } else {
                    pDialog.dismiss();
                    alertDialog.setMessage(response.body().getMessage());
                    alertDialog.show();


                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pDialog.dismiss();
                view.loginFailed(t);
            }
        });

    }
}
