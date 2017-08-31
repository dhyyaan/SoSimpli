package com.think360.sosimply;

import android.app.Application;
import android.content.SharedPreferences;

import com.raizlabs.android.dbflow.config.FlowManager;

import com.think360.sosimply.di.components.AppComponent;
import com.think360.sosimply.di.components.DaggerAppComponent;
import com.think360.sosimply.di.modules.ApplicationModule;
import com.think360.sosimply.di.modules.HttpModule;
import com.think360.sosimply.utils.RxBus;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class AppController extends Application {


    public static SharedPreferences sharedPreferencesCompat;

    public static SharedPreferences.Editor getSharedPrefEditor() {
        return sharedPreferencesCompat.edit();
    }

    private AppComponent component;

    private RxBus bus;
    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(this);
        bus = new RxBus();
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

    public RxBus bus() {
        return bus;
    }


    public AppComponent getComponent() {
        return component;
    }
}

