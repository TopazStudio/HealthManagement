package com.flycode.healthbloom.ui.appInitialization;

import com.flycode.healthbloom.models.User;
import com.flycode.healthbloom.ui.base.BasePresenter;

import javax.inject.Inject;

public class AppInitPresenter<V extends AppInitContract.AppInitView>
        extends BasePresenter<V>
        implements AppInitContract.AppInitPresenter<V> {

    @Inject
    User user;


    @Override
    public void onFinish() {
        //Do user persistence and goal checking
    }
}
