package com.flycode.healthbloom.ui.weight.WeightEntry;

import android.content.Context;

import com.flycode.healthbloom.data.models.WeightMeasurement;

import dagger.Module;
import dagger.Provides;

@Module
public class WeightEntryModule {
    @Provides
    Context provideWeightEntryActivityContext(WeightEntryActivity WeightEntryActivity){
        return WeightEntryActivity;
    }

    @Provides
    WeightEntryContract.WeightEntryPresenter<WeightEntryContract.WeightEntryView>
    provideWeightEntryPresenter() {
        return new WeightEntryPresenter<>();
    }

    @Provides
    WeightMeasurement provideWeightMeasurement(){
        return new WeightMeasurement();
    }
}
