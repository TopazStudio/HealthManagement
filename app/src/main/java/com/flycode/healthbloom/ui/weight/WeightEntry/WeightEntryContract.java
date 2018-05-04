package com.flycode.healthbloom.ui.weight.WeightEntry;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

public interface WeightEntryContract {
    interface WeightEntryView extends MvpView {
        void setupTagsInput();
    }
    interface WeightEntryPresenter<V extends WeightEntryContract.WeightEntryView> extends MvpPresenter<V> {
        void onSave();
        void fetchWeightEntry(int id);
        void fetchTags();
    }
}
