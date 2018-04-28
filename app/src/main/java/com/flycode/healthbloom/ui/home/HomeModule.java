package com.flycode.healthbloom.ui.home;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {
    @Provides
    Context provideHomeActivityContext(HomeActivity homeActivity){
        return homeActivity;
    }

    @Provides
    HomeContract.HomePresenter<HomeContract.HomeView>
    provideHomePresenter() {
        return new HomePresenter<>();
    }
}
