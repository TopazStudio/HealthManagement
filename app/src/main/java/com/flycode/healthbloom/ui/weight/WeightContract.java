package com.flycode.healthbloom.ui.weight;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

interface WeightContract {
    interface WeightView extends MvpView{

    }
    interface WeightPresenter<V extends WeightContract.WeightView> extends MvpPresenter<V> {

    }
}
