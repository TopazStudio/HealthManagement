package com.flycode.healthbloom.ui.weight.WeightOverview.BMIGraph;

import android.support.annotation.NonNull;

import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.data.models.WeightMeasurement_Table;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BMIGraphPresenter<V extends BMIGraphContract.BMIGraphView>
        extends BasePresenter<V>
        implements BMIGraphContract.BMIGraphPresenter<V>   {

    /**
     * Query database for WeightMeasurement models
     * and parse it to LineData then call view to set the Line Graphs with the data
     * */
    @Override
    public void getLineDataSet() {
        SQLite.select()
                .from(WeightMeasurement.class)
                .orderBy(WeightMeasurement_Table.Date,true)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<WeightMeasurement>() {

                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<WeightMeasurement> tResult) {

                        //Form entries from WeightMeasurements
                        List<Entry> BMIEntries = new ArrayList<>();

                        for (WeightMeasurement data : tResult) {
                            BMIEntries.add(new Entry(Objects.requireNonNull(data.Date).getTime(), data.BMI.get()));
                        }

                        getMvpView().setLineDataSet(new LineDataSet(BMIEntries, "BMI"));

                    }

                }).error(new Transaction.Error() {
                        @Override
                        public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                            getMvpView().showError(error.getMessage());
                        }
                }).execute();
    }
}
