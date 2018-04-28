package com.flycode.healthbloom.ui.home;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

interface HomeContract {
    interface HomeView extends MvpView {

    }
    interface HomePresenter<V extends HomeContract.HomeView> extends MvpPresenter<V> {

    }
}
