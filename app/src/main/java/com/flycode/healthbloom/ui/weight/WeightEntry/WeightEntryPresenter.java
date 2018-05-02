package com.flycode.healthbloom.ui.weight.WeightEntry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.data.models.User_Table;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.data.models.WeightMeasurement_Table;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.Date;
import java.util.Objects;

public class WeightEntryPresenter<V extends WeightEntryContract.WeightEntryView>
        extends BasePresenter<V>
        implements WeightEntryContract.WeightEntryPresenter<V>   {


    /**
     * Log the new Weight Measurement by the user
     *
     * */
    @Override
    public void onSave(WeightMeasurement weightMeasurement) {
        //TODO: better notification of registry and make async
        //TODO: show loading screen with async database insertion

        //Add height.
        if (weightMeasurement.Height.get() == 0f){
            //No height specified therefore use the default
            weightMeasurement.Height.set(getDefaultHeight());
        }

        //Add date.
        weightMeasurement.Date.set(new Date());

        //Add BMI
        //TODO: implement BMI library to handle BMI calculations
        weightMeasurement.BMI.set(29.0f);

        if(weightMeasurement.save()){
            getMvpView().showMessage("Successfully Saved");
            getMvpView().finishAndGoToParent();
        }else{
            getMvpView().showError("Error in registry");
        }
    }

    /**
     * Get the default height as the initial height given by the user
     * during App Initialization Module.
     *
     * */
    private float getDefaultHeight(){
        return Objects.requireNonNull(SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(1))
                .querySingle()).InitHeight.get();
    }

    /**
     * Fetch a WeightMeasurement model from the database with the specified id
     *
     * */
    @Override
    public void fetchWeightEntry(int id) {
        SQLite.select()
                .from(WeightMeasurement.class)
                .where(WeightMeasurement_Table.id.eq(id))
                .async()
                .querySingleResultCallback(new QueryTransaction.QueryResultSingleCallback<WeightMeasurement>() {

                    @Override
                    public void onSingleQueryResult(QueryTransaction transaction, @Nullable WeightMeasurement weightMeasurement) {
                        getMvpView().addUpdateWeightMeasurement(weightMeasurement);
                    }
                }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                getMvpView().showError(error.getMessage());
            }
        }).execute();
    }
}
