package com.flycode.healthbloom.ui.weight.weightOverview.WeightGraph;

import dagger.Module;
import dagger.Provides;

@Module
public class WeightGraphModule {

    @Provides
    WeightGraphContract.WeightGraphPresenter<WeightGraphContract.WeightGraphView>
    provideWeightGraphPresenter() {
        return new WeightGraphPresenter<>();
    }
}
