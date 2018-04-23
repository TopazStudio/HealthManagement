package com.flycode.healthbloom.ui.appInitialization.HeightDetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.models.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HeightDetailsFragment extends Fragment {

    @Inject
    User user;

    @BindView(R.id.txt_height)
    EditText txt_height;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_height_details, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    public void commitChanges(){
        user.setInitHeight(Integer.parseInt(txt_height.getText().toString()));
    }
}
