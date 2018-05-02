package com.flycode.healthbloom.ui.weight.WeightOverview;

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

    @Provides
    GraphViewPagerAdapter provideGraphViewPagerAdapter(WeightActivity weightActivity) {
        return new GraphViewPagerAdapter(weightActivity.getSupportFragmentManager());
    }

    @Provides
    EntryListAdapter provideEntryListAdapter() {
        return new EntryListAdapter();
    }
}
