package com.flycode.healthbloom.trackers;

public interface TrackerComponent {

    enum ResultCode {
        RESULT_OK,
        RESULT_UNKNOWN,       // we don't know if hw is present
        RESULT_NOT_SUPPORTED, // hw not present or not configured
        RESULT_NOT_ENABLED,   // hw is disabled (e.g bluetooth off)
        RESULT_ERROR,         // Component failed to initialize
        RESULT_ERROR_FATAL,   // Component failed, Tracker shouldn't start
        RESULT_PENDING        // will call callback
    }

    /**
     * Component name
     */
    String getName();

    /**
     * Called by Tracker during initialization
     */
    ResultCode onInit();

    /**
     * Called by Tracker when workout is paused
     */
    void onPause();

    /**
     * Called by Tracker when workout is playing
     */
    void onPlay();

    /**
     * Called by Tracker when workout is finished
     */
    ResultCode onFinish();

    public interface Callback {
        void run(TrackerComponent component, ResultCode resultCode);
    }
}

