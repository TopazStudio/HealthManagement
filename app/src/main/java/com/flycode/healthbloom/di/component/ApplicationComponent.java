package com.flycode.healthbloom.di.component;

import android.app.Application;

import com.flycode.healthbloom.di.module.ActivityBindingsModule;
import com.flycode.healthbloom.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBindingsModule.class,
        AppModule.class
})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {
    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {

        @BindsInstance
        ApplicationComponent.Builder application(Application application);

        ApplicationComponent build();
    }
}
