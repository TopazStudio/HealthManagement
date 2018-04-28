package com.flycode.healthbloom.ui.weight;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class WeightModule {
    @Provides
    Context provideWeightActivityContext(WeightActivity WeightActivity){
        return WeightActivity;
    }

    @Provides
    WeightContract.WeightPresenter<WeightContract.WeightView>
    provideWeightPresenter() {
        return new WeightPresenter<>();
    }
}
