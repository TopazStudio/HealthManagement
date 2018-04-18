package com.flycode.healthbloom.ui.base;

/**
 * Base interface that every presenter must implement.
 * Carries common functionality that every presenter must have.
 * Has a generic type to bond the presenter to the view completely.
 */
public interface MvpPresenter<V extends MvpView>  {
    /**
     * Store instance of view attached to.
     */
    void onAttach(V mvpView);

    /**
     * Clear instance of view and perform other cleaning
     * tasks
     * */
    void onDetach();
}
