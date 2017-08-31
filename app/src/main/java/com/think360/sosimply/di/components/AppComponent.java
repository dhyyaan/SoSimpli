package com.think360.sosimply.di.components;











import com.think360.sosimply.di.modules.ApplicationModule;
import com.think360.sosimply.di.modules.HttpModule;
import com.think360.sosimply.manager.ApiService;
import com.think360.sosimply.ui.activities.AddAvailabilityActivity;
import com.think360.sosimply.ui.activities.AssignedScheduleDeatilActivity;
import com.think360.sosimply.ui.activities.ChangeScheduleActivity;
import com.think360.sosimply.ui.activities.CompletedScheduleDetailActivity;
import com.think360.sosimply.ui.activities.CompletedSchedulesActivity;
import com.think360.sosimply.ui.activities.ContactToOperatorActivity;
import com.think360.sosimply.ui.activities.HomeActivity;
import com.think360.sosimply.ui.activities.NonApprovedActivity;
import com.think360.sosimply.ui.activities.SoSDetailActivity;
import com.think360.sosimply.ui.activities.login.ConfirmPasswordActivity;
import com.think360.sosimply.ui.activities.login.ForgetPasswordActivity;
import com.think360.sosimply.ui.activities.login.LoginActivity;
import com.think360.sosimply.ui.activities.login.VerifyOtpActivity;
import com.think360.sosimply.ui.fragments.AvailabilityFragment;
import com.think360.sosimply.ui.fragments.ProfileFragment;
import com.think360.sosimply.ui.fragments.ScheduleFragment;
import com.think360.sosimply.ui.fragments.SoSFragment;

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
    void inject(SoSDetailActivity activity);
    void inject(ConfirmPasswordActivity activity);
    void inject(ForgetPasswordActivity activity);
    void inject(LoginActivity activity);
    void inject(AddAvailabilityActivity activity);
    void inject(HomeActivity activity);
    void inject(CompletedSchedulesActivity activity);

    void inject(ScheduleFragment fragment);

    void inject(ProfileFragment fragment);

    void inject(AvailabilityFragment fragment);

    void inject(SoSFragment fragment);

    ApiService api();


    //  SharedPrefsHelper sharedPrefsHelper();
}
