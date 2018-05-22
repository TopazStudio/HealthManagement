package com.flycode.healthbloom.broadcastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import lombok.Setter;

public class ServiceStartedReceiver extends BroadcastReceiver {

    @Setter
    private OnServiceStartedListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        listener.onServiceStarted();
    }

    public interface OnServiceStartedListener{
        void onServiceStarted();
    }
}
