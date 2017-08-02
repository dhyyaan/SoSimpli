package com.think360.sosimpli.di.components;











import com.think360.sosimpli.di.modules.ApplicationModule;
import com.think360.sosimpli.di.modules.HttpModule;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.ui.activities.AddAvailabilityActivity;
import com.think360.sosimpli.ui.activities.HomeActivity;
import com.think360.sosimpli.ui.activities.login.ConfirmPasswordActivity;
import com.think360.sosimpli.ui.activities.login.ForgetPasswordActivity;
import com.think360.sosimpli.ui.activities.login.LoginActivity;
import com.think360.sosimpli.ui.activities.login.VerifyOtpActivity;
import com.think360.sosimpli.ui.fragments.AvailabilityFragment;
import com.think360.sosimpli.ui.fragments.ProfileFragment;
import com.think360.sosimpli.ui.fragments.ScheduleFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HttpModule.class, ApplicationModule.class})
public interface AppComponent {

    void inject(VerifyOtpActivity activity);
    void inject(ConfirmPasswordActivity activity);
    void inject(ForgetPasswordActivity activity);
    void inject(LoginActivity activity);
void inject(ScheduleFragment fragment);
    void inject(AddAvailabilityActivity activity);
    void inject(ProfileFragment fragment);
    void inject(AvailabilityFragment fragment);
    void inject(HomeActivity activity);
    ApiService api();


    //  SharedPrefsHelper sharedPrefsHelper();
}
