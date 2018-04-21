package com.flycode.healthbloom.ui.splash;

import android.os.Bundle;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.ui.base.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Splash screen activity. Displays logo and app name during
 * app startup
 * */
public class SplashActivity
        extends BaseView
        implements SplashContract.SplashMvpView {

    @Inject
    SplashContract.SplashMvpPresenter<SplashContract.SplashMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        presenter.onAttach(this);
        onInit();
    }

    public void onInit() {
        presenter.startCounting();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
