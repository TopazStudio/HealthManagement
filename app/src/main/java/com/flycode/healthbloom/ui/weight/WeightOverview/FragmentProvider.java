package com.flycode.healthbloom.ui.weight.WeightOverview;

import com.flycode.healthbloom.di.scope.PerFragment;
import com.flycode.healthbloom.ui.weight.WeightOverview.BMIGraph.BMIGraphFragment;
import com.flycode.healthbloom.ui.weight.WeightOverview.BMIGraph.BMIGraphModule;
import com.flycode.healthbloom.ui.weight.WeightOverview.WeightGraph.WeightGraphFragment;
import com.flycode.healthbloom.ui.weight.WeightOverview.WeightGraph.WeightGraphModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentProvider {

    @PerFragment
    @ContributesAndroidInjector(modules = BMIGraphModule.class)
    abstract BMIGraphFragment bmiGraphFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = WeightGraphModule.class)
    abstract WeightGraphFragment weightGraphFragment();
}
