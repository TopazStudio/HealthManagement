package com.flycode.healthbloom.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.HomeActivityBinding;
import com.flycode.healthbloom.ui.base.BaseViewWithNav;
import com.flycode.healthbloom.ui.exercise.exerciseEntry.ExerciseEntryActivity;
import com.flycode.healthbloom.ui.weight.weightEntry.WeightEntryActivity;

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

    public void onFabAdd(View view){
        if(homeActivityBinding.weightFabLayout.getVisibility() == View.VISIBLE
                && homeActivityBinding.stepsFabLayout.getVisibility() == View.VISIBLE){
            //WHEN VISIBLE
            closeFabButton();
        }else{
            //WHEN HIDDEN
            openFabButton();
        }
    }
    private void closeFabButton(){
        homeActivityBinding.fabAdd.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.anim_fab_button_onhide));
        homeActivityBinding.stepsFabLayout.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.hide_fab_hidden_buttons));
        homeActivityBinding.weightFabLayout.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.hide_fab_hidden_buttons));
        homeActivityBinding.weightFabLayout.setVisibility(View.GONE);
        homeActivityBinding.stepsFabLayout.setVisibility(View.GONE);
    }

    private void openFabButton(){
        homeActivityBinding.weightFabLayout.setVisibility(View.VISIBLE);
        homeActivityBinding.stepsFabLayout.setVisibility(View.VISIBLE);
        homeActivityBinding.fabAdd.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.anim_fab_button_onshow));
        homeActivityBinding.stepsFabLayout.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.show_fab_hidden_buttons));
        homeActivityBinding.weightFabLayout.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.show_fab_hidden_buttons));
    }

    public void onAddSteps(View view){
        closeFabButton();
        finishAndGoTo(ExerciseEntryActivity.class);
    }

    public void onAddWeight(View view){
        closeFabButton();
        finishAndGoTo(WeightEntryActivity.class);
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
