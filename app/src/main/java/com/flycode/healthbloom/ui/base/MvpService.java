package com.flycode.healthbloom.ui.base;

public interface MvpService {

    void sendSuccess(String message);

    void sendError(String error);

    void sendOnFinish(boolean success);

    void stopSelf();

}
