package com.flycode.healthbloom.ui.exercise.exerciseView;


import com.flycode.healthbloom.di.scope.PerFragment;
import com.flycode.healthbloom.ui.exercise.exerciseView.fragments.HeartRateDetailsFragment;
import com.flycode.healthbloom.ui.exercise.exerciseView.fragments.PaceDetailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentProvider {
    @PerFragment
    @ContributesAndroidInjector
    abstract PaceDetailsFragment paceDetailsFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract HeartRateDetailsFragment heartRateDetails();
}
