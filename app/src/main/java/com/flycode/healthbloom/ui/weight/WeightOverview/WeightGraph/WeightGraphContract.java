package com.flycode.healthbloom.ui.weight.WeightOverview.WeightGraph;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;
import com.github.mikephil.charting.data.LineDataSet;

public interface WeightGraphContract {
    interface WeightGraphView extends MvpView {
        void setLineDataSet(LineDataSet data);
    }
    interface WeightGraphPresenter<V extends WeightGraphContract.WeightGraphView> extends MvpPresenter<V> {
        void getPerDayLineDataSet();
        void getPerMonthLineDataSet();
    }
}
