package com.flycode.healthbloom.ui.weight.WeightEntry;

import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

public interface WeightEntryContract {
    interface WeightEntryView extends MvpView {
        void addUpdateWeightMeasurement(WeightMeasurement weightMeasurement);
    }
    interface WeightEntryPresenter<V extends WeightEntryContract.WeightEntryView> extends MvpPresenter<V> {
        void onSave(WeightMeasurement weightMeasurement);
        void fetchWeightEntry(int id);
    }
}
