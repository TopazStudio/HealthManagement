package com.flycode.healthbloom.ui.appInitialization;

import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.ui.base.BasePresenter;

public class AppInitPresenter<V extends AppInitContract.AppInitView>
        extends BasePresenter<V>
        implements AppInitContract.AppInitPresenter<V> {

    @Override
    public void onFinish(User user) {
        //TODO: better notification of registry and make async
        //TODO: show loading screen
        if(user.save()){
            getMvpView().showMessage("Successfully registered");
        }else{
            getMvpView().showError("Error in registry");
        }
    }
}
