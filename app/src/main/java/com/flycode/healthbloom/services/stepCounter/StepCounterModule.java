package com.flycode.healthbloom.services.stepCounter;

import android.content.Context;

import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.di.scope.PerService;
import com.flycode.healthbloom.trackers.TrackerComponentCollection;

import dagger.Module;
import dagger.Provides;

@Module
public class StepCounterModule {

    @Provides
    Context provideStepCounterService(StepCounterService stepCounterService){
        return stepCounterService;
    }

    @Provides
    @PerService
    StepCounterPresenter<StepCounterContract.StepCounterService> provideStepCounterPresenter(
            Steps steps,
            TrackerComponentCollection components,
            StepCounterService stepCounterService
    ){
        StepCounterPresenter
                <StepCounterContract.StepCounterService> presenter = new StepCounterPresenter<>();
        presenter.setSteps(steps);
        presenter.setComponents(components);
        presenter.setService(stepCounterService);
        return presenter;
    }

    @Provides
    @PerService
    TrackerComponentCollection provideTrackerComponentCollection(){
        return new TrackerComponentCollection();
    }

    @Provides
    @PerService
    Steps provideSteps(){
        return new Steps();
    }
}
