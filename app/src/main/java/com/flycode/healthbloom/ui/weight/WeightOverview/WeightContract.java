package com.flycode.healthbloom.ui.weight.WeightOverview;

import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

import java.util.List;

interface WeightContract {
    interface WeightView extends MvpView{
        void setWeightEntries(List<WeightMeasurement> weightEntries);
    }
    interface WeightPresenter<V extends WeightContract.WeightView> extends MvpPresenter<V> {
        void getWeightMeasurements();
    }
}
