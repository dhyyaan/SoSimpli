package com.think360.sosimpli.presenter;



import com.think360.sosimpli.AppController;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.model.user.User;
import com.think360.sosimpli.ui.activities.LoginActivity;
import com.think360.sosimpli.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

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
        apiService.loginUser(email, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body().getStatus()) {
                    pDialog.dismiss();

                    AppController.getSharedPrefEditor().putInt(AppConstants.DRIVER_ID, response.body().getData().getDriverId()).apply();
                    AppController.getSharedPrefEditor().putString(AppConstants.DRIVER_NAME, response.body().getData().getDriverName()).apply();
                    AppController.getSharedPrefEditor().putString(AppConstants.DRIVER_EMAIL, response.body().getData().getDriverEmail()).apply();
                 //   AppController.getSharedPrefEditor().putString(AppConstants.WORKER_PROFILE_IMAGE_URL, response.body().getData().getWorkerPic()).apply();

                    view.loginSuccessful(response.body().getData().getDriverName(), response.body().getData().getDriverId());

                } else {
                    pDialog.dismiss();
                    alertDialog.setMessage(response.body().getMessage());
                    alertDialog.show();
                    Timber.d("LOGIN_ELSE", response.body());

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
