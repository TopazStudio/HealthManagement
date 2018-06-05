package com.flycode.healthbloom.ui.base;

import android.os.Bundle;

public interface MvpService {

    void sendSuccess(String message);

    void sendError(String error);

    void sendOnFinish(boolean success, Bundle data);

    void stopSelf();

}
