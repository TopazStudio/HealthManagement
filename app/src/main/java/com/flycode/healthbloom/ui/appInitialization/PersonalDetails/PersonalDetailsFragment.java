package com.flycode.healthbloom.ui.appInitialization.PersonalDetails;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.PersonalDetailsBinding;
import com.flycode.healthbloom.models.User;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalDetailsFragment extends DaggerFragment {

    @Inject
    User user;

    public PersonalDetailsFragment() {
        // Required empty public constructor
    }

    public static PersonalDetailsFragment newInstance() {
        PersonalDetailsFragment fragment = new PersonalDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PersonalDetailsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_personal_details,container,false);
        binding.setUser(user);
        return binding.getRoot();
    }
}
