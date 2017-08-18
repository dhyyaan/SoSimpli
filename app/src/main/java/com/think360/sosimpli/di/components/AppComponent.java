package com.think360.sosimpli.di.components;











import com.think360.sosimpli.di.modules.ApplicationModule;
import com.think360.sosimpli.di.modules.HttpModule;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.ui.activities.AddAvailabilityActivity;
import com.think360.sosimpli.ui.activities.AssignedScheduleDeatilActivity;
import com.think360.sosimpli.ui.activities.ChangeScheduleActivity;
import com.think360.sosimpli.ui.activities.CompletedScheduleDetailActivity;
import com.think360.sosimpli.ui.activities.CompletedSchedulesActivity;
import com.think360.sosimpli.ui.activities.ContactToOperatorActivity;
import com.think360.sosimpli.ui.activities.HomeActivity;
import com.think360.sosimpli.ui.activities.NonApprovedActivity;
import com.think360.sosimpli.ui.activities.SoSDetailActivity;
import com.think360.sosimpli.ui.activities.login.ConfirmPasswordActivity;
import com.think360.sosimpli.ui.activities.login.ForgetPasswordActivity;
import com.think360.sosimpli.ui.activities.login.LoginActivity;
import com.think360.sosimpli.ui.activities.login.VerifyOtpActivity;
import com.think360.sosimpli.ui.fragments.AvailabilityFragment;
import com.think360.sosimpli.ui.fragments.ProfileFragment;
import com.think360.sosimpli.ui.fragments.ScheduleFragment;
import com.think360.sosimpli.ui.fragments.SoSFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HttpModule.class, ApplicationModule.class})
public interface AppComponent {
    void inject(CompletedScheduleDetailActivity activity);
    void inject(ContactToOperatorActivity activity);
    void inject(ChangeScheduleActivity activity);
    void inject(VerifyOtpActivity activity);

    void inject(NonApprovedActivity activity);

    void inject(AssignedScheduleDeatilActivity activity);

    void inject(SoSFragment fragment);

    void inject(SoSDetailActivity activity);
    void inject(ConfirmPasswordActivity activity);
    void inject(ForgetPasswordActivity activity);
    void inject(LoginActivity activity);
void inject(ScheduleFragment fragment);
    void inject(AddAvailabilityActivity activity);
    void inject(ProfileFragment fragment);
    void inject(AvailabilityFragment fragment);
    void inject(HomeActivity activity);
    void inject(CompletedSchedulesActivity activity);
    ApiService api();


    //  SharedPrefsHelper sharedPrefsHelper();
}
