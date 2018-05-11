package com.flycode.healthbloom.ui.weight.weightEntry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.flycode.healthbloom.data.models.Note;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.data.models.WeightMeasurement_Tag;
import com.flycode.healthbloom.ui.base.BasePresenter;
import com.flycode.healthbloom.utils.FileUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lombok.Setter;

public class WeightEntryPresenter<V extends WeightEntryContract.WeightEntryView>
        extends BasePresenter<V>
        implements WeightEntryContract.WeightEntryPresenter<V>   {


    @Setter
    WeightMeasurement weightMeasurement;
    @Setter
    List<Tag> tags;
    @Setter
    Note note;
    @Setter
    Calendar entryCalendar;

    private Bitmap imageBitmap;


    /**
     * Log the new Weight Measurement by the user
     *
     * */
    @Override
    public void onSave() {
        getMvpView().showLoading();

        //Add height.
        if (weightMeasurement.Height.get() == 0f){
            //No height specified therefore use the default
            weightMeasurement.Height.set(defaultUser.InitHeight.get());
        }
        weightMeasurement.Date.set(entryCalendar.getTime()); //Add date.
        weightMeasurement.BMI.set(29.0f); //Add BMI  //TODO: implement BMI library to handle BMI calculations
        note.weightMeasurement = weightMeasurement; //Add note

        beginSaveTransaction();
    }

    @Override
    public void onCaptureImageResult(final Intent data) {
        getMvpView().showProgressBar();
        getCompositeDisposable().add(Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) {
                        imageBitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                        getMvpView().setPhotoProgress(100);
                        emitter.onNext("complete");
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String String) {
                        getMvpView().setImageBitmap(imageBitmap);
                        getMvpView().hideProgressBar();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getMvpView().showError(throwable.getMessage());
                    }
                })
        );
    }

    @Override
    public void onPickerImageResult(final Intent data) {
        getMvpView().showProgressBar();
        getCompositeDisposable().add(
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        final Uri imageUri = data.getData();
                        getMvpView().setPhotoProgress(30);

                        InputStream imageStream = null;
                        if (imageUri != null) {
                            imageStream = getMvpView().getContentResolver().openInputStream(imageUri);
                        }
                        getMvpView().setPhotoProgress(50);

                        imageBitmap = BitmapFactory.decodeStream(imageStream);
                        getMvpView().setPhotoProgress(100);
                        emitter.onNext("complete");
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String String) {
                        getMvpView().setImageBitmap(imageBitmap);
                        getMvpView().hideProgressBar();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getMvpView().showError(throwable.getMessage());
                    }
                })
        );
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
                    tags = tResult;
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
     * Begin a transaction to save the models from Weight Entry Activity
     *
     * */
    private void beginSaveTransaction() {
        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {

                //Save Photo
                weightMeasurement.PhotoLocation = FileUtils.saveImage(imageBitmap,"/progress_photos");

                //Save WeightMeasurement
                weightMeasurement.save();

                //Save Tags
                for (Tag tag:tags) {
                    WeightMeasurement_Tag weightMeasurementTag = new WeightMeasurement_Tag();
                    weightMeasurementTag.setTag(tag);
                    weightMeasurementTag.setWeightMeasurement(weightMeasurement);
                    weightMeasurementTag.save();
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
                getMvpView().finishAndGoToParent();
            }
        })
        .error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                getMvpView().showError(error.getMessage());
            }
        })
        .build();
        transaction.execute();
    }
}
