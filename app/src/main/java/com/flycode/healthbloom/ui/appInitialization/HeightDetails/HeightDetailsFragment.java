package com.flycode.healthbloom.ui.appInitialization.HeightDetails;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.HeightDetailsBinding;
import com.flycode.healthbloom.data.models.User;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class HeightDetailsFragment extends DaggerFragment {

    @Inject
    User user;

    private HeightDetailsBinding binding;

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
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_height_details,container,false);
        binding.setUser(user);

        init();
        return binding.getRoot();
    }

    private void init(){
        setUpHeightUnitsSpinner();
    }

    @SuppressWarnings("unchecked")
    private void setUpHeightUnitsSpinner(){
        List<String> data = Arrays.asList(getResources().getStringArray(R.array.height_units));

        binding.heightUnitsPicker.setWheelAdapter(new ArrayWheelAdapter(getContext())); // text data source
        binding.heightUnitsPicker.setSkin(WheelView.Skin.None);
        binding.heightUnitsPicker.setLoop(false);
        binding.heightUnitsPicker.setWheelSize(data.size());
        binding.heightUnitsPicker.setSelection(0);
        binding.heightUnitsPicker.setWheelData(data);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 12;
        style.selectedTextColor = Color.WHITE;
        style.textColor = Color.LTGRAY;
        style.backgroundColor = Color.TRANSPARENT;
        binding.heightUnitsPicker.setStyle(style);

        binding.heightUnitsPicker.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                user.InitHeightUnits.set((String) o);
            }
        });
    }
}
