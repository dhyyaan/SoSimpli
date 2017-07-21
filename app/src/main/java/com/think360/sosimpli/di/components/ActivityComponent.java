package com.think360.sosimpli.di.components;



import com.think360.sosimpli.di.modules.ActivityModule;
import com.think360.sosimpli.di.scopes.PerActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {


}
