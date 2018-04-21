package com.flycode.healthbloom.ui.appInitialization.WeightDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;

import butterknife.ButterKnife;

public class WeightDetailsFragment extends Fragment {

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight_details, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
}
