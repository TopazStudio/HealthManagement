package com.flycode.healthbloom.utils.BindingAdapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.widget.RadioGroup;

import com.flycode.healthbloom.R;

public class GenderBindingAdapter {

    /**
     * Method to set gender value from the Model to the view.
     * */
    @BindingAdapter("gender")
    public static void setGender(RadioGroup view, String gender) {
        //Check the gender given
        if (gender != null){
            if(gender.equals("Male")){
                view.check(R.id.male_radio_btn);
            }else if (gender.equals("Male")){
                view.check(R.id.female_radio_btn);
            }
        }
    }

    /**
     * Method to get gender value from the view to the Model.
     * */
    @InverseBindingAdapter(attribute = "gender", event = "genderAttrChanged")
    public static String getGender(RadioGroup view) {
        if (view.getCheckedRadioButtonId() == R.id.male_radio_btn){
            return "Male";
        }else if (view.getCheckedRadioButtonId() == R.id.female_radio_btn){
            return "Female";
        }else {
            return null;
        }
    }

    /**
     * Event to notify DataBinding framework when to get gender value from the view.
     * */
    @BindingAdapter("genderAttrChanged")
    public static void onGenderChange(RadioGroup view, final InverseBindingListener genderAttrChanged) {
        view.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (genderAttrChanged != null) {
                    genderAttrChanged.onChange();
                }
            }
        });
    }

}
