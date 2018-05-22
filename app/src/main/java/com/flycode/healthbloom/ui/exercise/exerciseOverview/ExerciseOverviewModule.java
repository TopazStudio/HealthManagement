package com.flycode.healthbloom.ui.exercise.exerciseOverview;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ExerciseOverviewModule {

    @Provides
    Context provideExerciseOverviewActivityContext(ExerciseOverviewActivity exerciseOverviewActivity){
        return exerciseOverviewActivity;
    }

    @Provides
    ExerciseOverviewContract.ExerciseOverviewPresenter<ExerciseOverviewContract.ExerciseOverviewView>
    provideWeightPresenter() {
        return new ExerciseOverviewPresenter<>();
    }

    @Provides
    ExerciseEntryListAdapter provideEntryListAdapter() {
        return new ExerciseEntryListAdapter();
    }
}
