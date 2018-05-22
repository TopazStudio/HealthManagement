package com.flycode.healthbloom.ui.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.databinding.BaseActivityBinding;
import com.flycode.healthbloom.databinding.BaseNavDrawerHeadingBindings;
import com.flycode.healthbloom.ui.exercise.exerciseOverview.ExerciseOverviewActivity;
import com.flycode.healthbloom.ui.foodNutrition.foodNutritionOverview.FoodNutritionOverviewActivity;
import com.flycode.healthbloom.ui.home.HomeActivity;
import com.flycode.healthbloom.ui.tags.TagsActivity;
import com.flycode.healthbloom.ui.weight.weightOverview.WeightOverviewActivity;

import javax.inject.Inject;
import javax.inject.Named;


public class BaseViewWithNav
        extends BaseView
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    @Named("default_user")
    User user;

    protected BaseActivityBinding baseActivityBinding;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    protected BaseNavDrawerHeadingBindings baseNavDrawerHeadingBindings;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MAIN CONTENT VIEW
        baseActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);

        //DRAWER HEADING
        baseNavDrawerHeadingBindings = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.base_navigation_drawer_header,null,false);
        baseNavDrawerHeadingBindings.setUser(user);

        //DRAWER LISTENER
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, baseActivityBinding.baseDrawerLayout,
                R.string.navigation_open,R.string.navigation_close);
        baseActivityBinding.baseDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        //NAV VIEW
        baseActivityBinding.baseNavView.addHeaderView(baseNavDrawerHeadingBindings.getRoot());
        baseActivityBinding.baseNavView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState)  {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_module_menu_item:
                finishAndGoTo(HomeActivity.class);
                break;
            case R.id.weight_module_menu_item:
                finishAndGoTo(WeightOverviewActivity.class);
                break;
            case R.id.food_module_menu_item:
                finishAndGoTo(FoodNutritionOverviewActivity.class);
                break;
            case R.id.exercise_module_menu_item:
                finishAndGoTo(ExerciseOverviewActivity.class);
                break;
            case R.id.tags_menu_item:
                finishAndGoTo(TagsActivity.class);
                break;
            case R.id.exit_menu_item:
                finish();
                break;
        }
        return true;
    }

}
