package com.flycode.healthbloom.ui.foodNutrition.foodNutritionOverview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.FoodNutritionOverviewBinding;
import com.flycode.healthbloom.ui.base.BaseViewWithNav;

import java.util.Objects;

import javax.inject.Inject;

public class FoodNutritionOverviewActivity
        extends BaseViewWithNav
        implements FoodNutritionOverviewContract.FoodNutritionOverviewView {

    private FoodNutritionOverviewBinding foodNutritionOverviewBinding;

    @Inject
    FoodNutritionOverviewContract.FoodNutritionOverviewPresenter
            <FoodNutritionOverviewContract.FoodNutritionOverviewView> presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ADD CONTENT
        foodNutritionOverviewBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_food_nutrition_overview,null,false);
        baseActivityBinding.contentFrame.addView(foodNutritionOverviewBinding.getRoot());

        //PRESENTER
        presenter.onAttach(this);

        //TOOLBAR
        setSupportActionBar((Toolbar) foodNutritionOverviewBinding.toolbar);
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
