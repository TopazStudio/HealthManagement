package com.flycode.healthbloom.trackers;

public enum TrackerState {
    INIT(Constants.TRACKER_STATE.INIT),                // initial state
    INITIALIZING(Constants.TRACKER_STATE.INITIALIZING),// initializing components
    INITIALIZED(Constants.TRACKER_STATE.INITIALIZED),  // initialized
    CONNECTING(Constants.TRACKER_STATE.CONNECTING),    // connecting to e.g GPS
    CONNECTED(Constants.TRACKER_STATE.CONNECTED),      // connected, ready to start
    STARTED(Constants.TRACKER_STATE.STARTED),          // Workout started
    PAUSED(Constants.TRACKER_STATE.PAUSED),            // Workout paused
    STOPPED(Constants.TRACKER_STATE.STOPPED),          // Workout stopped (i.e save screen open)
    CLEANUP(Constants.TRACKER_STATE.CLEANUP),          // Cleaning up components
    ERROR(Constants.TRACKER_STATE.ERROR);              // Components failed to initialize

    private final int value;

    TrackerState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TrackerState valueOf(int val) {
        switch (val) {
            case Constants.TRACKER_STATE.INIT:
                return INIT;
            case Constants.TRACKER_STATE.INITIALIZING:
                return INITIALIZING;
            case Constants.TRACKER_STATE.INITIALIZED:
                return INITIALIZED;
            case Constants.TRACKER_STATE.STARTED:
                return STARTED;
            case Constants.TRACKER_STATE.PAUSED:
                return PAUSED;
            case Constants.TRACKER_STATE.CLEANUP:
                return CLEANUP;
            case Constants.TRACKER_STATE.ERROR:
                return ERROR;
            case Constants.TRACKER_STATE.CONNECTING:
                return CONNECTING;
            case Constants.TRACKER_STATE.CONNECTED:
                return CONNECTED;
            case Constants.TRACKER_STATE.STOPPED:
                return STOPPED;
        }
        return null;
    }

    public static boolean equals(TrackerState oldVal, TrackerState newVal) {
        if (oldVal != null && newVal != null)
            return oldVal.getValue() == newVal.getValue();
        if (oldVal == null && newVal == null)
            return true;
        return false;
    }
}
