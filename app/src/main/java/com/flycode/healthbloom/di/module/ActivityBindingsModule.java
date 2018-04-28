package com.flycode.healthbloom.di.module;

import com.flycode.healthbloom.di.scope.PerActivity;
import com.flycode.healthbloom.ui.appInitialization.AppInitActivity;
import com.flycode.healthbloom.ui.appInitialization.AppInitModule;
import com.flycode.healthbloom.ui.appInitialization.FragmentProvider;
import com.flycode.healthbloom.ui.home.HomeActivity;
import com.flycode.healthbloom.ui.home.HomeModule;
import com.flycode.healthbloom.ui.splash.SplashActivity;
import com.flycode.healthbloom.ui.splash.SplashActivityModule;
import com.flycode.healthbloom.ui.weight.WeightActivity;
import com.flycode.healthbloom.ui.weight.WeightModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingsModule {
    @PerActivity
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity splashActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {AppInitModule.class, FragmentProvider.class})
    abstract AppInitActivity appInitActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = WeightModule.class)
    abstract WeightActivity weightActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity homeActivity();
}
