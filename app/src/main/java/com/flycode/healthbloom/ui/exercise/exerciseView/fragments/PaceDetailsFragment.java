package com.flycode.healthbloom.ui.exercise.exerciseView.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.databinding.PaceDetailsBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaceDetailsFragment extends DaggerFragment {


    @Inject
    Steps steps;

    PaceDetailsBinding paceDetailsBinding;

    public PaceDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        paceDetailsBinding = DataBindingUtil.inflate(inflater,
                R.layout.pace_details_page,container,false);
        paceDetailsBinding.setSteps(steps);

        return paceDetailsBinding.getRoot();
    }

}
