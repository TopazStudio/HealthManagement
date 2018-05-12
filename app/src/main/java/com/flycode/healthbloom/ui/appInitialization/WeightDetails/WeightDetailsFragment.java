package com.flycode.healthbloom.ui.appInitialization.WeightDetails;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.databinding.WeightDetailsBinding;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WeightDetailsFragment extends DaggerFragment {

    @Inject
    User user;

    private WeightDetailsBinding binding;

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
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_weight_details,container,false);
        binding.setUser(user);

        init();
        return binding.getRoot();
    }

    private void init(){
        //CONFIGURE UI
        setUpWeightUnitsSpinner();
    }

    @SuppressWarnings("unchecked")
    private void setUpWeightUnitsSpinner(){
        List<String> data = Arrays.asList(getResources().getStringArray(R.array.weight_units));

        binding.weightUnitsPicker.setWheelAdapter(new ArrayWheelAdapter(getContext())); // text data source
        binding.weightUnitsPicker.setSkin(WheelView.Skin.None);
        binding.weightUnitsPicker.setLoop(false);
        binding.weightUnitsPicker.setWheelSize(data.size());
        binding.weightUnitsPicker.setSelection(0);
        binding.weightUnitsPicker.setWheelData(data);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 12;
        style.selectedTextColor = Color.WHITE;
        style.textColor = Color.LTGRAY;
        style.backgroundColor = Color.TRANSPARENT;
        binding.weightUnitsPicker.setStyle(style);

        binding.weightUnitsPicker.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                user.InitWeightUnits.set((String) o);
                binding.targetWeightUnits.setText((String) o);
            }
        });
    }
}
