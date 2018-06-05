package com.flycode.healthbloom.ui.exercise.exerciseView;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

public interface ExerciseViewContract {
    interface ExerciseViewView extends MvpView {
        void setupTagsInput();
        void addMultipleTags();
        void toggleMode();
    }
    interface ExerciseViewPresenter<V extends ExerciseViewContract.ExerciseViewView> extends MvpPresenter<V> {
        void fetchAllTags();
        void fetchStepsTags();
        void onSave();
        void onDelete();
    }
}
