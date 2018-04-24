package com.flycode.healthbloom.ui.appInitialization.DetailsOverview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.DetailsOverviewBinding;
import com.flycode.healthbloom.models.User;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class DetailsOverviewFragment extends DaggerFragment {

    @Inject
    User user;

    public DetailsOverviewFragment() {
        // Required empty public constructor
    }

    public static DetailsOverviewFragment newInstance() {
        DetailsOverviewFragment fragment = new DetailsOverviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DetailsOverviewBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_details_overview,container,false);
        binding.setUser(user);
        return binding.getRoot();
    }
}
