package com.flycode.healthbloom.broadcastRecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import lombok.Setter;

public class PlayOrPauseReceiver extends BroadcastReceiver {

    @Setter
    private OnPlayOrPauseListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        listener.onPlayOrPause();
    }

    public interface OnPlayOrPauseListener {
        void onPlayOrPause();
    }
}
