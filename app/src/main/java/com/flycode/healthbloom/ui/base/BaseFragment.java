package com.flycode.healthbloom.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Objects;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment implements MvpView {
    private BaseView baseActivity;
    private Dialog progressDialog;

    @Override
    public void finishAndGoTo(Class<? extends Activity> next) {
        startActivity(new Intent(getActivity(),next));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseView) {
            baseActivity = (BaseView) context;
        }
    }

    protected abstract void setUp(View view);

    @Override
    public void showLoading() {
        hideLoading();
        Dialog progressDialog = new Dialog(Objects.requireNonNull(this.getContext()));

        progressDialog.show();

        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

//        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void showMessage(String message) {
        if (baseActivity != null)
            baseActivity.showMessage(message);
    }

    @Override
    public void showError(String message) {
        if (baseActivity != null)
            baseActivity.showError(message);
    }

    public BaseView getBaseActivity() {
        return baseActivity;
    }
}
