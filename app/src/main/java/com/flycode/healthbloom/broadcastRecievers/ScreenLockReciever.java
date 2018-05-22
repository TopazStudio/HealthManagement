package com.flycode.healthbloom.broadcastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import lombok.Setter;

public class ScreenLockReciever extends BroadcastReceiver {

    @Setter
    private OnScreenLockListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        listener.onScreenLock();
    }

    public interface OnScreenLockListener{
        void onScreenLock();
    }
}
