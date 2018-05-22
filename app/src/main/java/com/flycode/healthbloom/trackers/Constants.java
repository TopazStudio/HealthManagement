package com.flycode.healthbloom.trackers;

public class Constants {
    public interface TRACKER_STATE {
        public static final int INIT = 0;         // initial state
        public static final int INITIALIZING = 1; // initializing components
        public static final int INITIALIZED = 2;  // initialized
        public static final int STARTED = 3;      // Workout started
        public static final int PAUSED = 4;       // Workout paused
        public static final int CLEANUP = 5;      // Cleaning up components
        public static final int ERROR = 6;        // Components failed to initialize ;
        public static final int CONNECTING = 7;
        public static final int CONNECTED = 8;
        public static final int STOPPED = 9;
    }
}
