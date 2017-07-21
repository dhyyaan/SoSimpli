package com.think360.sosimpli;

import android.app.Application;
import android.content.SharedPreferences;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.think360.sosimpli.di.components.AppComponent;
import com.think360.sosimpli.di.components.DaggerAppComponent;
import com.think360.sosimpli.di.modules.ApplicationModule;
import com.think360.sosimpli.di.modules.HttpModule;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class AppController extends Application {


    public static SharedPreferences sharedPreferencesCompat;

    public static SharedPreferences.Editor getSharedPrefEditor() {
        return sharedPreferencesCompat.edit();
    }

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();


        FlowManager.init(this);

        sharedPreferencesCompat = getSharedPreferences("APP_PREF", MODE_PRIVATE);

        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();

        Timber.plant(new Timber.DebugTree());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SourceSansProRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }


    public AppComponent getComponent() {
        return component;
    }
}

