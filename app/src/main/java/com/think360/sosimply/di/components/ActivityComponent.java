package com.think360.sosimply.di.components;



import com.think360.sosimply.di.modules.ActivityModule;
import com.think360.sosimply.di.scopes.PerActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {


}
