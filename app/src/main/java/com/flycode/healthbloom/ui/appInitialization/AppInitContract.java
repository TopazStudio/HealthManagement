package com.flycode.healthbloom.ui.appInitialization;

import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

public interface AppInitContract {
    interface AppInitView extends MvpView {}

    interface AppInitPresenter<V extends AppInitView> extends MvpPresenter<V> {
        void onFinish(User user);
    }
}
