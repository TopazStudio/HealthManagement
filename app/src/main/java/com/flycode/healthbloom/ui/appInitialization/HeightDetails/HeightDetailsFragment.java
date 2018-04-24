package com.flycode.healthbloom.ui.appInitialization.HeightDetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.HeightDetailsBinding;
import com.flycode.healthbloom.models.User;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class HeightDetailsFragment extends DaggerFragment {

    @Inject
    User user;

    public HeightDetailsFragment() {
        // Required empty public constructor
    }

    public static HeightDetailsFragment newInstance() {
        HeightDetailsFragment fragment = new HeightDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HeightDetailsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_height_details,container,false);
        binding.setUser(user);
        return binding.getRoot();
    }
}
