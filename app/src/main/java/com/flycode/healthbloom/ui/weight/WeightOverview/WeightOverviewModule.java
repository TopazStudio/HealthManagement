package com.flycode.healthbloom.ui.weight.WeightOverview;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class WeightOverviewModule {
    @Provides
    Context provideWeightActivityContext(WeightOverviewActivity weightOverviewActivity){
        return weightOverviewActivity;
    }

    @Provides
    WeightOverviewContract.WeightPresenter<WeightOverviewContract.WeightView>
    provideWeightPresenter() {
        return new WeightOverviewPresenter<>();
    }

    @Provides
    GraphViewPagerAdapter provideGraphViewPagerAdapter(WeightOverviewActivity weightOverviewActivity) {
        return new GraphViewPagerAdapter(weightOverviewActivity.getSupportFragmentManager());
    }

    @Provides
    EntryListAdapter provideEntryListAdapter() {
        return new EntryListAdapter();
    }
}
