package com.flycode.healthbloom.ui.splash;

import android.os.CountDownTimer;

import com.flycode.healthbloom.ui.appInitialization.AppInitActivity;
import com.flycode.healthbloom.ui.base.BasePresenter;

public class SplashPresenter<V extends SplashContract.SplashMvpView>
        extends BasePresenter<V>
        implements SplashContract.SplashMvpPresenter<V> {

    private static final int WAITING_TIME = 3000;

    @Override
    public void startCounting() {
        new CountDownTimer(WAITING_TIME,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                getMvpView().finishAndGoTo(AppInitActivity.class);
            }
        }.start();
    }
}
