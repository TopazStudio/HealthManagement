package com.flycode.healthbloom.ui.weight.WeightEntry;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.databinding.WeightEntryBinding;
import com.flycode.healthbloom.ui.base.BaseView;

import javax.inject.Inject;

public class WeightEntryActivity
        extends BaseView
        implements WeightEntryContract.WeightEntryView  {

    @Inject
    WeightEntryContract.WeightEntryPresenter<WeightEntryContract.WeightEntryView> presenter;
    @Inject
    WeightMeasurement weightMeasurement;

    private WeightEntryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weight_entry);
        binding.setWeightMeasurement(weightMeasurement);
        presenter.onAttach(this);
        setSupportActionBar((Toolbar) binding.toolbar);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
