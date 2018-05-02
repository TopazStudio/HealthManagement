package com.flycode.healthbloom.di.module;

import com.flycode.healthbloom.di.scope.PerActivity;
import com.flycode.healthbloom.ui.appInitialization.AppInitActivity;
import com.flycode.healthbloom.ui.appInitialization.AppInitModule;
import com.flycode.healthbloom.ui.home.HomeActivity;
import com.flycode.healthbloom.ui.home.HomeModule;
import com.flycode.healthbloom.ui.splash.SplashActivity;
import com.flycode.healthbloom.ui.splash.SplashActivityModule;
import com.flycode.healthbloom.ui.weight.WeightEntry.WeightEntryActivity;
import com.flycode.healthbloom.ui.weight.WeightEntry.WeightEntryModule;
import com.flycode.healthbloom.ui.weight.WeightOverview.WeightActivity;
import com.flycode.healthbloom.ui.weight.WeightOverview.WeightModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingsModule {
    @PerActivity
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity splashActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {AppInitModule.class, com.flycode.healthbloom.ui.appInitialization.FragmentProvider.class})
    abstract AppInitActivity appInitActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {WeightModule.class, com.flycode.healthbloom.ui.weight.WeightOverview.FragmentProvider.class})
    abstract WeightActivity weightActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity homeActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = WeightEntryModule.class)
    abstract WeightEntryActivity weightEntryActivity();
}
