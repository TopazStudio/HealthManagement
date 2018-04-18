package com.flycode.healthbloom.ui.splash;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashActivityModule {

    @Provides
    SplashContract.SplashMvpPresenter<SplashContract.SplashMvpView>
    provideSplashMvpPresenter() {
        return new SplashPresenter<>();
    }
}
