package com.flycode.healthbloom.services.stepCounter;

import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.trackers.DurationTracker;
import com.flycode.healthbloom.trackers.TrackerComponentCollection;
import com.flycode.healthbloom.ui.base.BaseServicePresenter;

import lombok.Setter;

public class StepCounterPresenter<V extends StepCounterContract.StepCounterService>
    extends BaseServicePresenter<V>
    implements StepCounterContract.StepCounterPresenter<V>{

    @Setter
    Steps steps;
    @Setter
    TrackerComponentCollection components;

    /**
     * Log the new Weight Measurement by the user
     *
     * */
    @Override
    public void save(StepCounterService stepCounterService){
        DurationTracker durationTracker = (DurationTracker) components.getComponent("DurationTracker");
        steps.StartTime.set(durationTracker.getStartTime());
        steps.EndTime.set(durationTracker.getEndTime());
        steps.Calories.set(0); //TODO: get calories consumed
        steps.CalorieUnits.set("kcal(s)"); //TODO: get calorie units
        steps.Distance.set(0); //TODO: get distance
        steps.DistanceUnits.set("km(s)");//TODO: get distance units

        if (steps.save()){
            if (stepCounterService != null){
                stepCounterService.sendSuccess("Successfully registered");
                stepCounterService.sendOnFinish(true);
                stepCounterService.stopSelf();
            }
        }else{
            getService().sendError("Sorry. Something went wrong. Please try again");
        }
    }

    /**
     * Registers the change in steps. The calories consumed
     * and distance taken is determined.
     *
     * */
    @Override
    public void calculateCalories(int distance){
        steps.Distance.set(distance);
    }
}
