package com.flycode.healthbloom;

import com.flycode.healthbloom.di.component.ApplicationComponent;
import com.flycode.healthbloom.di.component.DaggerApplicationComponent;
import com.raizlabs.android.dbflow.config.FlowManager;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class HealthBloomApplication extends DaggerApplication {
    public ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize DBFlow
        FlowManager.init(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent appComponent =  DaggerApplicationComponent.builder().application(this).build();
        appComponent.inject(this);
        this.appComponent = appComponent;
        return appComponent;
    }
}
