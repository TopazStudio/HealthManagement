package com.flycode.healthbloom.ui.appInitialization.WeightDetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.WeightDetailsBinding;
import com.flycode.healthbloom.models.User;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WeightDetailsFragment extends DaggerFragment {

    @Inject
    User user;

    public WeightDetailsFragment(){

    }

    public static WeightDetailsFragment newInstance() {
        WeightDetailsFragment fragment = new WeightDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WeightDetailsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_weight_details,container,false);
        binding.setUser(user);
        return binding.getRoot();
    }
}
