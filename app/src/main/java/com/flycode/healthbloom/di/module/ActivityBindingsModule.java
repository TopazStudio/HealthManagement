package com.flycode.healthbloom.di.module;

import com.flycode.healthbloom.di.scope.PerActivity;
import com.flycode.healthbloom.ui.splash.SplashActivity;
import com.flycode.healthbloom.ui.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = AndroidInjectionModule.class)
public abstract class ActivityBindingsModule {

    @PerActivity
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

}
