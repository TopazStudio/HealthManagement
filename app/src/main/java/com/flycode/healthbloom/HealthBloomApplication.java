package com.flycode.healthbloom;

import com.flycode.healthbloom.di.component.ApplicationComponent;
import com.flycode.healthbloom.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class HealthBloomApplication extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent appComponent =  DaggerApplicationComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
