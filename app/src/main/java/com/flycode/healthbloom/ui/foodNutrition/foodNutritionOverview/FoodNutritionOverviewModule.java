package com.flycode.healthbloom.ui.foodNutrition.foodNutritionOverview;


import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class FoodNutritionOverviewModule {
    @Provides
    Context provideCalorieOverviewActivityContext
            (FoodNutritionOverviewActivity foodNutritionOverviewActivity){
        return foodNutritionOverviewActivity;
    }

    @Provides
    FoodNutritionOverviewContract.FoodNutritionOverviewPresenter
            <FoodNutritionOverviewContract.FoodNutritionOverviewView>
    provideCalorieOverviewPresenter() {
        return new FoodNutritionOverviewPresenter<>();
    }
}
