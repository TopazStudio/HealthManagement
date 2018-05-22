package com.flycode.healthbloom.ui.exercise.exerciseOverview;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.data.models.Steps_Table;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.flycode.healthbloom.ui.exercise.exerciseEntry.ExerciseEntryActivity;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

public class ExerciseOverviewPresenter<V extends ExerciseOverviewContract.ExerciseOverviewView>
        extends BasePresenter<V>
        implements ExerciseOverviewContract.ExerciseOverviewPresenter<V>  {

    /**
     * Fetches the Weight Entries from the local database asynchronously
     * then presents the data to the view once completed.
     *
     * */
    @Override
    public void getSteps() {
        SQLite.select()
                .from(Steps.class)
                .orderBy(Steps_Table.EndTime,true)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Steps>() {

                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Steps> tResult) {
                        getMvpView().setStepsEntries(tResult);
                    }

                }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                getMvpView().showError(error.getMessage());
            }
        }).execute();
    }

    /**
     * Opens the ExerciseEntryActivity for results on adding steps.
     * */
    @Override
    public void addSteps() {
        getMvpView().openForResult(ExerciseEntryActivity.class,
                ExerciseOverviewActivity.ADD_STEPS_REQUEST_CODE,null);
    }

    /**
     * Opens the WeightEntryActivity for results on updating a weight.
     * */
    @Override
    public void onViewSteps(Steps steps) {
        Bundle bundle = new Bundle();
        bundle.putString(ExerciseOverviewActivity.STEPS_VIEW_EXTRA,
                new Gson().toJson(steps));
        getMvpView().openForResult(ExerciseEntryActivity.class,
                ExerciseOverviewActivity.VIEW_STEPS_REQUEST_CODE,null/*bundle*/);
    }
}
