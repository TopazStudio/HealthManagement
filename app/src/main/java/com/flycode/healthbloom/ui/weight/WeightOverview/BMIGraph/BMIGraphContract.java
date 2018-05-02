package com.flycode.healthbloom.ui.weight.WeightOverview.BMIGraph;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;
import com.github.mikephil.charting.data.LineDataSet;

public interface BMIGraphContract {
    interface BMIGraphView extends MvpView {
        void setLineDataSet(LineDataSet data);
        void refresh();
    }
    interface BMIGraphPresenter<V extends BMIGraphContract.BMIGraphView> extends MvpPresenter<V> {
        void getLineDataSet();
    }
}
