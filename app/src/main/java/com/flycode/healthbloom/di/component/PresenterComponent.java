package com.flycode.healthbloom.di.component;

import com.flycode.healthbloom.di.module.DatabaseModule;
import com.flycode.healthbloom.ui.base.UtilityWrapper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DatabaseModule.class)
public interface PresenterComponent {
    void inject(UtilityWrapper utilityWrapper);
}
