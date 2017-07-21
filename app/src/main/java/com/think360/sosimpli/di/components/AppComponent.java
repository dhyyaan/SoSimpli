package com.think360.sosimpli.di.components;











import com.think360.sosimpli.di.modules.ApplicationModule;
import com.think360.sosimpli.di.modules.HttpModule;
import com.think360.sosimpli.manager.ApiService;
import com.think360.sosimpli.ui.activities.HomeActivity;
import com.think360.sosimpli.ui.activities.LoginActivity;
import com.think360.sosimpli.ui.fragments.ProfileFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HttpModule.class, ApplicationModule.class})
public interface AppComponent {

    void inject(LoginActivity fragment);
    void inject(ProfileFragment fragment);
    void inject(HomeActivity activity);
    ApiService api();


    //  SharedPrefsHelper sharedPrefsHelper();
}
