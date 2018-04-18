package com.flycode.healthbloom.ui.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Base class of presenters. Provides methods managing the view presenter
 * relationship.
 * */
public class BasePresenter <V extends MvpView> implements MvpPresenter<V>{
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private V mvpView;

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onDetach() {
        this.mvpView = null;
    }

    /**
     * Get CompositeDisposable of presenter
     * */
    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    /**
     * Retrieve instance of view attached to
     * */
    public V getMvpView() {
        return mvpView;
    }

    /**
     * Check if there is a view attached to this presenter
     * */
    public boolean isViewAttached() {
        return mvpView != null;
    }

    /**
     * Check if there is a view attached to this presenter
     * */
    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
