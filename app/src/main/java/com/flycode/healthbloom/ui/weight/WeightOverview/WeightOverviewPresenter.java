package com.flycode.healthbloom.ui.weight.WeightOverview;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.data.models.WeightMeasurement_Table;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.flycode.healthbloom.ui.weight.WeightEntry.WeightEntryActivity;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

class WeightOverviewPresenter<V extends WeightOverviewContract.WeightView>
        extends BasePresenter<V>
        implements WeightOverviewContract.WeightPresenter<V>  {

    /**
     * Fetches the Weight Entries from the local database asynchronously
     * then presents the data to the view once completed.
     *
     * */
    @Override
    public void getWeightMeasurements() {
        SQLite.select()
                .from(WeightMeasurement.class)
                .orderBy(WeightMeasurement_Table.Date,true)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<WeightMeasurement>() {

                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<WeightMeasurement> tResult) {
                        getMvpView().setWeightEntries(tResult);
                    }

                }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                getMvpView().showError(error.getMessage());
            }
        }).execute();
    }

    /**
     * Opens the WeightEntryActivity for results on adding a weight.
     * */
    @Override
    public void addWeight() {
        getMvpView().openForResult(WeightEntryActivity.class,1,null);
    }

    /**
     * Opens the WeightEntryActivity for results on updating a weight.
     * */
    @Override
    public void onUpdateWeight(WeightMeasurement weightMeasurement) {
        Bundle bundle = new Bundle();
        bundle.putInt("id",weightMeasurement.id);
        getMvpView().openForResult(WeightEntryActivity.class,1,bundle);
    }
}
