package com.flycode.healthbloom.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import java.util.Objects;

import dagger.android.DaggerService;
import io.reactivex.disposables.CompositeDisposable;
import lombok.Getter;
import lombok.Setter;

public class BaseService extends DaggerService implements MvpService{
    @Getter
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Setter
    private ServiceEventReceiver serviceEventReceiver;
    private PowerManager.WakeLock mWakeLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void sendSuccess(String message){
        if(serviceEventReceiver != null)
            serviceEventReceiver.onSuccess(message);
    }

    public void sendError(String error){
        if(serviceEventReceiver != null)
            serviceEventReceiver.onError(error);
    }

    public void sendOnFinish(boolean success, Bundle data){
        if(serviceEventReceiver != null)
            serviceEventReceiver.onFinish(success,data);
    }

    public interface ServiceEventReceiver {
        void onError(String error);
        void onSuccess(String message);
        void onFinish(boolean success,Bundle data);
    }

    protected void wakeLock(boolean get) {
        if (mWakeLock != null) {
            if (mWakeLock.isHeld()) {
                mWakeLock.release();
            }
            mWakeLock = null;
        }
        if (get) {
            PowerManager pm = (PowerManager) this
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = Objects.requireNonNull(pm).newWakeLock(PowerManager.FULL_WAKE_LOCK,
                    "HealthUpgrade");
            if (mWakeLock != null) {
                mWakeLock.acquire();
            }
        }
    }
}
