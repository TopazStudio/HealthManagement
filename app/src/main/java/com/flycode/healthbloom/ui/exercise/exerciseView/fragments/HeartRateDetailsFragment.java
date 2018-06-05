package com.flycode.healthbloom.ui.exercise.exerciseView.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.databinding.HeartRateDetailsBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeartRateDetailsFragment extends DaggerFragment {


    @Inject
    Steps steps;

    HeartRateDetailsBinding heartRateDetailsBinding;

    public HeartRateDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        heartRateDetailsBinding = DataBindingUtil.inflate(inflater,
                R.layout.heart_rate_details_page,container,false);
        heartRateDetailsBinding.setSteps(steps);

        return heartRateDetailsBinding.getRoot();
    }

}
