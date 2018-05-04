package com.flycode.healthbloom.di.component;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(dependencies = ApplicationComponent.class)
public interface PresenterComponent {
//    void inject(BasePresenter<? extends MvpView> basePresenter);
}
