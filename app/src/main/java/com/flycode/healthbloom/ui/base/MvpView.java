package com.flycode.healthbloom.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Base interface that every view must implement.
 * Carries common functionality that every view must have
 * */
public interface MvpView {
    /**
     * Navigate to another activity and exit the current one
     * */
    void finishAndGoTo(Class<? extends Activity> next);

    /**
     * Navigate to home/parent activity and exit the current one
     * */
    void finishAndGoToParent();

    /**
     * Navigate to another activity for results.
     * */
    void openForResult(Class<? extends Activity> next, int requestCode, @Nullable Bundle data);

    /**
     * Show loading animation. Loading animation can be customized
     * here.
     * */
    void showLoading();

    /**
     * Hide loading animation
     * */
    void hideLoading();

    /**
     * Display message to user. Info Message can be customized here.
     * */
    void showMessage(String message);

    /**
     * Display error message to user. Error message can be customized here
     * */
    void showError(String message);
}
