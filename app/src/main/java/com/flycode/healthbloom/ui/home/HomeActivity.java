package com.flycode.healthbloom.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.HomeActivityBinding;
import com.flycode.healthbloom.ui.base.BaseView;

import javax.inject.Inject;

public class HomeActivity
        extends BaseView
        implements HomeContract.HomeView {

    @Inject
    HomeContract.HomePresenter<HomeContract.HomeView> presenter;

    private HomeActivityBinding binding;

    /**
     * Initialize data-binding on the activities layout and attach the presenter.
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        presenter.onAttach(this);
    }

    /**
     * Detaches the presenter from the activity.
     *
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
