package com.flycode.healthbloom.services.stepCounter;

import com.flycode.healthbloom.ui.base.MvpService;
import com.flycode.healthbloom.ui.base.MvpServicePresenter;

interface StepCounterContract {
    interface StepCounterService extends MvpService {
    }
    interface StepCounterPresenter<V extends StepCounterContract.StepCounterService> extends MvpServicePresenter<V>{
        void save(com.flycode.healthbloom.services.stepCounter.StepCounterService stepCounterService);
        void calculateCalories(int distance);
    }
}
