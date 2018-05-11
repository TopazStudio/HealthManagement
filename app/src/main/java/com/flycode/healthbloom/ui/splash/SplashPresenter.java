package com.flycode.healthbloom.ui.splash;

import android.os.CountDownTimer;

import com.flycode.healthbloom.ui.appInitialization.AppInitActivity;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.flycode.healthbloom.ui.home.HomeActivity;

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
                if (isUserRegistered())
                    getMvpView().finishAndGoTo(HomeActivity.class);
                else
                    getMvpView().finishAndGoTo(AppInitActivity.class);
            }
        }.start();
    }

    private boolean isUserRegistered(){
        return defaultUser != null;
    }
}
