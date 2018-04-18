package com.flycode.healthbloom.ui.splash;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

/**
 * Contract between the view and presenter declaring actions that
 * each can invoke from each other.
 * */
public interface SplashContract {
    interface SplashMvpView extends MvpView {

    }

    interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    }
}
