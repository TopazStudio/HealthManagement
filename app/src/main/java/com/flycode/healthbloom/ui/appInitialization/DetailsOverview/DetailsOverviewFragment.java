package com.flycode.healthbloom.ui.appInitialization.DetailsOverview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.models.User;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsOverviewFragment extends Fragment {

    @Inject
    User user;

    @BindView(R.id.tv_full_name)
    TextView tv_full_name;
    @BindView(R.id.tv_gender)
    TextView tv_gender;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_dob)
    TextView tv_dob;
    @BindView(R.id.tv_height)
    TextView tv_height;
    @BindView(R.id.tv_weight)
    TextView tv_weight;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_overview, container, false);
        ButterKnife.bind(this,view);
//        init();
        return view;
    }

    //TODO: make this a global generic helper for date manipulation and retrieval
    /**
     * Helper method for getting the date in a Calendar object as
     * string
     * */
    private String getDob(Calendar cal){
        int yr = cal.get(Calendar.YEAR);
        int mon = cal.get(Calendar.MONTH);
        int dy = cal.get(Calendar.DAY_OF_MONTH);

        return dy + "/" + mon + "/" + yr;
    }

    /**
     * Display data from the previous forms to allow user
     * to confirm whether he entered a correct thing or not.
     * */
    private void init(){
        tv_full_name.setText(user.getFullname());
        tv_gender.setText(user.getGender());
        tv_age.setText(user.getAge());
        tv_dob.setText(getDob(user.getDOB()));
        tv_height.setText(user.getInitHeight());
        tv_weight.setText(user.getInitWeight());
    }
}
