package com.flycode.healthbloom.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import dagger.android.support.DaggerAppCompatActivity;

public class BaseView extends DaggerAppCompatActivity implements MvpView{
    private Dialog progressDialog;
    private static final String TAG = BaseView.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void finishAndGoTo(Class<? extends Activity> next) {
            startActivity(new Intent(this,next));
            finish();
    }


    @Override
    public void showLoading() {
        hideLoading();
        //TODO: implement activity loading


    }

    @Override
    public void hideLoading() {
        //TODO: implement activity loading
    }

    @Override
    public void showMessage(String message) {
        //TODO: implement a better way of showing messages
        if (message != null)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        //TODO: implement a better way of showing errors
        if (message != null)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
