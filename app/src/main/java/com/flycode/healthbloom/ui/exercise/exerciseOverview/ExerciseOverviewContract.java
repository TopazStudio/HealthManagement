package com.flycode.healthbloom.ui.exercise.exerciseOverview;

import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

import java.util.List;

interface ExerciseOverviewContract {
    interface ExerciseOverviewView extends MvpView {
        void setStepsEntries(List<Steps> stepsEntries);
    }
    interface ExerciseOverviewPresenter
            <V extends ExerciseOverviewContract.ExerciseOverviewView>
            extends MvpPresenter<V> {
        void onViewSteps(Steps steps);

        void getSteps();

        void addSteps();
    }
}
