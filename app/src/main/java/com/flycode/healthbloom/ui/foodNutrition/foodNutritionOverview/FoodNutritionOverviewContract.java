package com.flycode.healthbloom.ui.foodNutrition.foodNutritionOverview;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

public interface FoodNutritionOverviewContract {
    interface FoodNutritionOverviewView extends MvpView {

    }
    interface FoodNutritionOverviewPresenter<V extends FoodNutritionOverviewView> extends MvpPresenter<V> {

    }
}
