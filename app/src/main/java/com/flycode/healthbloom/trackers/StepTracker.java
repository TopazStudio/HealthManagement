package com.flycode.healthbloom.trackers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.flycode.healthbloom.broadcastRecievers.ScreenLockReciever;
import com.flycode.healthbloom.services.stepCounter.StepCounterService;
import com.flycode.healthbloom.trackers.filters.StepDetector;

public class StepTracker implements TrackerComponent,
        SensorEventListener,ScreenLockReciever.OnScreenLockListener {
    //TODO: create a parent tracker service that holds the lifecycle of a tracking service
    private final StepCounterService context;
    private final StepListener listener;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor sensor;

    //TODO: create a parent tracker service that holds the lifecycle of a tracking service
    public StepTracker(StepCounterService context) {
        this.context = context;
        this.listener = (StepListener) context;
    }

    /*############################### SENSOR EVENT LISTENER ################################*/

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    /*############################### SCREEN LOCK LISTENER ################################*/

    @Override
    public void onScreenLock() {
        //Try waking the accelerometer
        if (context.isPlaying()){
            sensorManager.unregisterListener(this);
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }
    /*############################### TRACKER COMPONENT ################################*/

    @Override
    public String getName() {
        return "StepTracker";
    }

    @Override
    public ResultCode onInit() {
        //INIT MANAGER
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null){
            return ResultCode.RESULT_NOT_SUPPORTED;
        }

        //INIT SENSOR
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor == null){
            return ResultCode.RESULT_NOT_SUPPORTED;
        }

        //INIT FILTER
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(listener);
        return ResultCode.RESULT_OK;
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(this); //stop the sensor
    }

    @Override
    public void onPlay() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST); //start the sensor
    }

    @Override
    public ResultCode onFinish() {
        sensorManager = null;
        sensor = null;
        simpleStepDetector = null;
        return ResultCode.RESULT_OK;
    }

    /*############################### STEP DETECTOR LISTENER ################################*/

    public interface StepListener {
        void step(long timeNs);
    }
}
