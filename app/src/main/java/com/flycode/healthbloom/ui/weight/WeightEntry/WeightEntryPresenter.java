package com.flycode.healthbloom.ui.weight.WeightEntry;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.flycode.healthbloom.data.models.Note;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.data.models.WeightMeasurement_Table;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.Date;
import java.util.List;

import lombok.Setter;

public class WeightEntryPresenter<V extends WeightEntryContract.WeightEntryView>
        extends BasePresenter<V>
        implements WeightEntryContract.WeightEntryPresenter<V>   {

    @Setter
    User user;
    @Setter
    WeightMeasurement weightMeasurement;
    @Setter
    List<Tag> tags;
    @Setter
    Note note;

    /**
     * Log the new Weight Measurement by the user
     *
     * */
    @Override
    public void onSave() {
        //TODO: better notification of registry and make async
        //TODO: show loading screen with async database insertion

        //Add height.
        if (weightMeasurement.Height.get() == 0f){
            //No height specified therefore use the default
            weightMeasurement.Height.set(user.InitHeight.get());
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
                    public void onSingleQueryResult(QueryTransaction transaction, @Nullable WeightMeasurement w) {
                        weightMeasurement = w;
                    }
                }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                getMvpView().showError(error.getMessage());
            }
        }).execute();
    }

    @Override
    public void fetchTags() {
        SQLite.select()
            .from(Tag.class)
            .async()
            .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Tag>() {
                @Override
                public void onListQueryResult(QueryTransaction transaction, @NonNull List<Tag> tResult) {
                    tags = tResult;
                    getMvpView().setupTagsInput();
                }
            })
            .error(new Transaction.Error() {
                @Override
                public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                    getMvpView().showError(error.getMessage());
                }
            });
    }
}
