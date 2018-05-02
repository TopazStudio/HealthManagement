package com.flycode.healthbloom.ui.weight.WeightOverview.WeightGraph;

import android.support.annotation.NonNull;

import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeightGraphPresenter<V extends WeightGraphContract.WeightGraphView>
        extends BasePresenter<V>
        implements WeightGraphContract.WeightGraphPresenter<V>   {
    /**
     * Query database for WeightMeasurement models
     * and parse it to LineData then call view to set the Line Graphs with the data
     * */
    @Override
    public void getPerDayLineDataSet() {
        SQLite.select()
                .from(WeightMeasurement.class)
//                .orderBy(WeightMeasurement_Table.Date,true)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<WeightMeasurement>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<WeightMeasurement> tResult) {
                        //Form entries from WeightMeasurements
                        List<Entry> weightEntries = new ArrayList<>();
                        for (WeightMeasurement data : tResult) {
                            weightEntries.add(new Entry(Objects.requireNonNull(data.Date.get()).getTime(), data.Weight.get()));
                        }
                        getMvpView().setLineDataSet(new LineDataSet(weightEntries, "Weight"));
                    }
                }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                getMvpView().showError(error.getMessage());
            }
        }).execute();
    }

    //TODO: return per month average
    @Override
    public void getPerMonthLineDataSet() {
        SQLite.select()
                .from(WeightMeasurement.class)
//                .orderBy(WeightMeasurement_Table.Date,true)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<WeightMeasurement>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<WeightMeasurement> tResult) {
                        //Form entries from WeightMeasurements
                        List<Entry> weightEntries = new ArrayList<>();
                        for (WeightMeasurement data : tResult) {
                            weightEntries.add(new Entry(Objects.requireNonNull(data.Date.get()).getTime(), data.Weight.get()));
                        }
                        getMvpView().setLineDataSet(new LineDataSet(weightEntries, "Weight"));
                    }
                }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                getMvpView().showError(error.getMessage());
            }
        }).execute();
    }
}
