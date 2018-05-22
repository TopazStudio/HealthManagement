package com.flycode.healthbloom.ui.base;

public interface MvpServicePresenter<V extends MvpService> {
    /**
     * Store instance of view attached to.
     */
    void onAttach(V mvpService);

    /**
     * Clear instance of view and perform other cleaning
     * tasks
     * */
    void onDetach();
}
