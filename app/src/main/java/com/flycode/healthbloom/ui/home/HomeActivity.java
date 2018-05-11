package com.flycode.healthbloom.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.HomeActivityBinding;
import com.flycode.healthbloom.ui.base.BaseViewWithNav;

import java.util.Objects;

import javax.inject.Inject;

public class HomeActivity
        extends BaseViewWithNav
        implements HomeContract.HomeView {

    @Inject
    HomeContract.HomePresenter<HomeContract.HomeView> presenter;

    private HomeActivityBinding homeActivityBinding;

    /**
     * Initialize data-binding on the activities layout and attach the presenter.
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ADD CONTENT
        homeActivityBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_home,null,false);
        baseActivityBinding.contentFrame.addView(homeActivityBinding.getRoot());

        //PRESENTER
        presenter.onAttach(this);

        //TOOLBAR
        setSupportActionBar((Toolbar) homeActivityBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //INIT
        init();
    }

    private void init() {

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
