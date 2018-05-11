package com.flycode.healthbloom.ui.appInitialization;

import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.flycode.healthbloom.ui.weight.weightOverview.WeightOverviewActivity;

public class AppInitPresenter<V extends AppInitContract.AppInitView>
        extends BasePresenter<V>
        implements AppInitContract.AppInitPresenter<V> {

    @Override
    public void onFinish(User user) {
        //TODO: better notification of registry and make async
        //TODO: show loading screen with async database insertion
        if(user.save()){
            getMvpView().showMessage("Successfully registered");
            getMvpView().finishAndGoTo(WeightOverviewActivity.class);
        }else{
            getMvpView().showError("Error in registry");
        }
    }
}
