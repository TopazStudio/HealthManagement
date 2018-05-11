package com.flycode.healthbloom.ui.weight.weightOverview;

import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

import java.util.List;

interface WeightOverviewContract {
    interface WeightView extends MvpView{
        void setWeightEntries(List<WeightMeasurement> weightEntries);
    }
    interface WeightPresenter<V extends WeightOverviewContract.WeightView> extends MvpPresenter<V> {
        void getWeightMeasurements();
        void addWeight();
        void onUpdateWeight(WeightMeasurement weightMeasurement);
    }
}
