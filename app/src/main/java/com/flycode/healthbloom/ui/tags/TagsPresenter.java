package com.flycode.healthbloom.ui.tags;

import android.support.annotation.NonNull;

import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

public class TagsPresenter<V extends TagsContract.TagsView>
        extends BasePresenter<V>
        implements TagsContract.TagsPresenter<V> {


    @Override
    public void addTag(Tag tag) {
        if(tag.save()){
            getMvpView().showMessage("Successfully Added");
            getMvpView().addTag(tag);
        }else{
            getMvpView().showError("Sorry Something went wrong. Please try again.");
        }
    }

    @Override
    public void deleteTag(Tag tag) {
        if(tag.delete()){
            getMvpView().showMessage("Successfully deleted");
            getMvpView().deleteTag(tag);
        }else{
            getMvpView().showError("Sorry Something went wrong. Please try again.");
        }
    }

    /**
     * Fetches all users tags.
     * */
    @Override
    public void fetchTags() {
        SQLite.select()
                .from(Tag.class)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<Tag>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Tag> tResult) {
                        getMvpView().addMultipleTags(tResult);
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

    @Override
    public void updateTag(Tag tag,int index) {
        if(tag.save()){
            getMvpView().showMessage("Successfully Updated");
            getMvpView().updateTag(tag,index);
        }else{
            getMvpView().showError("Sorry Something went wrong. Please try again.");
        }
    }
}
