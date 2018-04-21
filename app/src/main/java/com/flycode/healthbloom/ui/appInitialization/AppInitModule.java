package com.flycode.healthbloom.ui.appInitialization;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppInitModule {
    @Provides
    Context provideHomeActivityContext(AppInitActivity appInitActivity){
        return appInitActivity;
    }

    @Provides
    ViewPagerAdapter provideViewPagerAdapter(AppInitActivity appInitActivity) {
        return new ViewPagerAdapter(appInitActivity.getSupportFragmentManager());
    }

    @Provides
    AppInitContract.AppInitPresenter<AppInitContract.AppInitView>
    provideAppInitPresenter() {
        return new AppInitPresenter<>();
    }
}
