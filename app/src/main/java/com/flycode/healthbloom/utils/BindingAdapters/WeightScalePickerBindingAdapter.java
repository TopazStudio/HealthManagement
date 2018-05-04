package com.flycode.healthbloom.utils.BindingAdapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;

import com.flycode.healthbloom.customUI.WeightScalePicker;

@BindingMethods({
        @BindingMethod(type = WeightScalePicker.class, attribute = "maximumAcceptedSize", method = "setMaximumAcceptedSize"),
        @BindingMethod(type = WeightScalePicker.class, attribute = "typeOfUnits", method = "setTypeOfUnits"),
        @BindingMethod(type = WeightScalePicker.class, attribute = "weight", method = "setWeight"),
})
@InverseBindingMethods({
        @InverseBindingMethod(type = WeightScalePicker.class, attribute = "weight", method = "getWeight",event = "weightAttrChanged"),
})
public class WeightScalePickerBindingAdapter {

    @BindingAdapter(value = "weightAttrChanged")
    public static void onWeightChanged(WeightScalePicker view, final InverseBindingListener attrChange) {
        view.setOnWeightChangedListener(new WeightScalePicker.OnWeightChangedListener() {
            @Override
            public void OnWeightChanged(float weight, String typeOfUnits) {
                if (attrChange != null) {
                    attrChange.onChange();
                }
            }
        });
    }

}



