package com.flycode.healthbloom.ui.weight;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.WeightActivityBinding;
import com.flycode.healthbloom.ui.base.BaseView;

import javax.inject.Inject;

public class WeightActivity
        extends BaseView
        implements WeightContract.WeightView {

    @Inject
    WeightContract.WeightPresenter<WeightContract.WeightView> presenter;

    private WeightActivityBinding binding;

    /**
     * Initialize data-binding on the activities layout and attach the presenter.
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weight);
        presenter.onAttach(this);
    }

    /**
     * Detaches the presenter from the activity.
     *
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
