package com.flycode.healthbloom.ui.exercise.exerciseView;

import android.support.annotation.NonNull;

import com.flycode.healthbloom.data.models.Note;
import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.data.models.Steps_Tag;
import com.flycode.healthbloom.data.models.Steps_Tag_Table;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

public class ExerciseViewPresenter<V extends ExerciseViewContract.ExerciseViewView>
        extends BasePresenter<V>
        implements ExerciseViewContract.ExerciseViewPresenter<V>  {

    @Setter
    Note note;
    @Setter
    List<Tag> viewTags;
    @Setter
    List<Tag> editTags;
    @Setter
    Steps steps;

    /**
     * Update the steps
     *
     * */
    @Override
    public void onSave() {
        getMvpView().showLoading();

        steps.note = note; //Add note

        beginSaveTransaction();
    }

    @Override
    public void onDelete(){

    }

    /**
     * Fetches all users tags.
     * */
    @Override
    public void fetchAllTags() {
        SQLite.select()
                .from(Tag.class)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Tag>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Tag> tResult) {
                        editTags = tResult;
                        getMvpView().setupTagsInput();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                        getMvpView().showError(error.getMessage());
                    }
                })
                .execute();
    }

    /**
     * Fetches all users tags.
     * */
    @Override
    public void fetchStepsTags() {
        SQLite.select()
                .from(Steps_Tag.class)
                .where(Steps_Tag_Table.steps_id.eq(steps.id))
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Steps_Tag>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Steps_Tag> tResult) {
                        List<Tag> listToReturn = new ArrayList<>();

                        for (Steps_Tag steps_tag:tResult) {
                            listToReturn.add(steps_tag.getTag());
                        }

                        viewTags = listToReturn;
                        getMvpView().addMultipleTags();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                        getMvpView().showError(error.getMessage());
                    }
                })
                .execute();
    }

    /**
     * Begin a transaction to save the models from Weight Entry Activity
     *
     * */
    private void beginSaveTransaction() {
        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {

                //Save WeightMeasurement
                steps.save();

                //Save Tags
                for (Tag tag:editTags) {
                    Steps_Tag steps_tag = new Steps_Tag();
                    steps_tag.setTag(tag);
                    steps_tag.setSteps(steps);
                    steps_tag.save();
                }

                //Save Note
                note.save();
            }
        })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@NonNull Transaction transaction) {
                        getMvpView().hideLoading();
                        getMvpView().showMessage("Successfully Saved");
                        fetchStepsTags();
                        getMvpView().toggleMode();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                        getMvpView().hideLoading();
                        getMvpView().showError(error.getMessage());
                    }
                })
                .build();
        transaction.execute();
    }
}
