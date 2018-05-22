package com.flycode.healthbloom.trackers;

import java.util.HashMap;

public class TrackerComponentCollection implements TrackerComponent {

    private final HashMap<String, TrackerComponent> components = new HashMap<>();

    public TrackerComponent addComponent(TrackerComponent component) {
        components.put(component.getName(), component);
        return component;
    }

    public TrackerComponent getComponent(String key) {
        synchronized (components) {
            if (components.containsKey(key))
                return components.get(key);

            return null;
        }
    }

    @Override
    public String getName() {
        return "TrackerComponentCollection";
    }

    /**
     * Called by Tracker during initialization
     */
    @Override
    public ResultCode onInit() {
        for (TrackerComponent component : components.values()) {
            component.onInit();
        }
        return ResultCode.RESULT_OK;
    }

    /**
     * Called by Tracker when workout is paused
     */
    @Override
    public void onPause() {
        for (TrackerComponent component : components.values()) {
            component.onPause();
        }
    }

    /**
     * Called by Tracker when workout is resumed
     */
    @Override
    public void onPlay() {
        for (TrackerComponent component : components.values()) {
            component.onPlay();
        }
    }

    /**
     * Called by tracked after workout has ended
     */
    @Override
    public ResultCode onFinish() {
        for (TrackerComponent component : components.values()) {
            component.onFinish();
        }
        return ResultCode.RESULT_OK;
    }
}

