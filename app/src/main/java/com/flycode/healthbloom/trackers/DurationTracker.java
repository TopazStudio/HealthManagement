package com.flycode.healthbloom.trackers;

import android.annotation.SuppressLint;
import android.os.Handler;

import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

public class DurationTracker implements TrackerComponent {

    private Handler customHandler = new Handler();
    private Runnable updateTimerThread;
    @Getter
    private Date startTime;
    @Getter
    private int seconds = 0;
    @Getter
    private String durationInString;

    public DurationTracker(final OnDurationChangedListener listener) {
        //SETUP TIMER
        updateTimerThread = new Runnable() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void run() {
                int hrs = seconds/3600;
                int mins = (seconds%3600)/60;
                int secs = seconds%60;

                if (listener != null)
                    listener.onDurationChanged(
                            durationInString = String.format("%02d",hrs) + ":"
                                               + String.format("%02d",mins) + ":"
                                               + String.format("%02d",secs)
                    );

                seconds++;
                customHandler.postDelayed(this,1000); //Loop
            }
        };
    }

    public Date getEndTime(){
        return new Date(startTime.getTime() + (seconds * 1000));
    }

    @Override
    public String getName() {
        return "DurationTracker";
    }

    @Override
    public ResultCode onInit() {
        startTime = Calendar.getInstance().getTime();
        return null;
    }

    @Override
    public void onPause() {
        customHandler.removeCallbacks(updateTimerThread); //stop the timer
    }

    @Override
    public void onPlay() {
        customHandler.postDelayed(updateTimerThread,0); //start the timer
    }

    @Override
    public ResultCode onFinish() {
        return null;
    }

    public interface OnDurationChangedListener{
        void onDurationChanged(String duration);
    }
}
