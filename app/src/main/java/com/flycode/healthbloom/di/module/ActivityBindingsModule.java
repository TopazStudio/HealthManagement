package com.flycode.healthbloom.di.module;

import com.flycode.healthbloom.di.scope.PerActivity;
import com.flycode.healthbloom.di.scope.PerService;
import com.flycode.healthbloom.services.stepCounter.StepCounterModule;
import com.flycode.healthbloom.services.stepCounter.StepCounterService;
import com.flycode.healthbloom.ui.appInitialization.AppInitActivity;
import com.flycode.healthbloom.ui.appInitialization.AppInitModule;
import com.flycode.healthbloom.ui.exercise.exerciseEntry.ExerciseEntryActivity;
import com.flycode.healthbloom.ui.exercise.exerciseOverview.ExerciseOverviewActivity;
import com.flycode.healthbloom.ui.exercise.exerciseOverview.ExerciseOverviewModule;
import com.flycode.healthbloom.ui.foodNutrition.foodNutritionOverview.FoodNutritionOverviewActivity;
import com.flycode.healthbloom.ui.foodNutrition.foodNutritionOverview.FoodNutritionOverviewModule;
import com.flycode.healthbloom.ui.home.HomeActivity;
import com.flycode.healthbloom.ui.home.HomeModule;
import com.flycode.healthbloom.ui.splash.SplashActivity;
import com.flycode.healthbloom.ui.splash.SplashActivityModule;
import com.flycode.healthbloom.ui.tags.TagsActivity;
import com.flycode.healthbloom.ui.tags.TagsModule;
import com.flycode.healthbloom.ui.weight.weightEntry.WeightEntryActivity;
import com.flycode.healthbloom.ui.weight.weightEntry.WeightEntryModule;
import com.flycode.healthbloom.ui.weight.weightOverview.WeightOverviewActivity;
import com.flycode.healthbloom.ui.weight.weightOverview.WeightOverviewModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingsModule {
    @PerActivity
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity splashActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {AppInitModule.class, com.flycode.healthbloom.ui.appInitialization.FragmentProvider.class})
    abstract AppInitActivity appInitActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {WeightOverviewModule.class, com.flycode.healthbloom.ui.weight.weightOverview.FragmentProvider.class})
    abstract WeightOverviewActivity weightActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity homeActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = WeightEntryModule.class)
    abstract WeightEntryActivity weightEntryActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = TagsModule.class)
    abstract TagsActivity tagsActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = FoodNutritionOverviewModule.class)
    abstract FoodNutritionOverviewActivity foodNutritionOverviewActivity();

    @PerActivity
    @ContributesAndroidInjector
    abstract ExerciseEntryActivity exerciseEntryActivity();

    @PerService
    @ContributesAndroidInjector(modules = StepCounterModule.class)
    abstract StepCounterService stepCounterService();

    @PerActivity
    @ContributesAndroidInjector(modules = ExerciseOverviewModule.class)
    abstract ExerciseOverviewActivity exerciseOverviewActivity();
}
