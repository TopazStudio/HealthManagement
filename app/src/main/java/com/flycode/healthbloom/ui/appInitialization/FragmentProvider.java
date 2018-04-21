package com.flycode.healthbloom.ui.appInitialization;


import com.flycode.healthbloom.di.scope.PerFragment;
import com.flycode.healthbloom.ui.appInitialization.DetailsOverview.DetailsOverviewFragment;
import com.flycode.healthbloom.ui.appInitialization.DetailsOverview.DetailsOverviewModule;
import com.flycode.healthbloom.ui.appInitialization.HeightDetails.HeightDetailsFragment;
import com.flycode.healthbloom.ui.appInitialization.HeightDetails.HeightDetailsModule;
import com.flycode.healthbloom.ui.appInitialization.PersonalDetails.PersonalDetailsFragment;
import com.flycode.healthbloom.ui.appInitialization.PersonalDetails.PersonalDetailsModule;
import com.flycode.healthbloom.ui.appInitialization.WeightDetails.WeightDetailsFragment;
import com.flycode.healthbloom.ui.appInitialization.WeightDetails.WeightDetailsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentProvider {

    @PerFragment
    @ContributesAndroidInjector(modules = PersonalDetailsModule.class)
    abstract PersonalDetailsFragment personalDetailsFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = WeightDetailsModule.class)
    abstract WeightDetailsFragment weightDetailsFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = HeightDetailsModule.class)
    abstract HeightDetailsFragment heightDetailsFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = DetailsOverviewModule.class)
    abstract DetailsOverviewFragment detailsOverviewFragment();
}