package com.flycode.healthbloom.ui.weight.weightOverview.BMIGraph;

import dagger.Module;
import dagger.Provides;

@Module
public class BMIGraphModule {
    @Provides
    BMIGraphContract.BMIGraphPresenter<BMIGraphContract.BMIGraphView>
    provideBMIGraphPresenter() {
        return new BMIGraphPresenter<>();
    }
}
