package com.flycode.healthbloom.ui.weight.weightEntry;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;

import com.flycode.healthbloom.ui.base.MvpPresenter;
import com.flycode.healthbloom.ui.base.MvpView;

public interface WeightEntryContract {
    interface WeightEntryView extends MvpView {
        void setupTagsInput();

        void hideProgressBar();

        void setPhotoProgress(int progress);
        void setImageBitmap(Bitmap imageBitmap);
        void showProgressBar();
        ContentResolver getContentResolver();
    }
    interface WeightEntryPresenter<V extends WeightEntryContract.WeightEntryView> extends MvpPresenter<V> {
        void onSave();
        void setDoImageSave(boolean doSave);
        void onCaptureImageResult(Intent data);
        void onPickerImageResult(Intent data);
        void fetchTags();
    }
}
