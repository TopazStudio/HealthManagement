package com.flycode.healthbloom.ui.weight.WeightEntry;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

public interface WeightEntryContract {
    interface WeightEntryView extends MvpView {

    }
    interface WeightEntryPresenter<V extends WeightEntryContract.WeightEntryView> extends MvpPresenter<V> {

        void logWeight();
    }
}
