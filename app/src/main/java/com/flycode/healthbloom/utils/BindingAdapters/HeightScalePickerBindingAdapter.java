package com.flycode.healthbloom.utils.BindingAdapters;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;

import com.flycode.healthbloom.customUI.HeightScalePicker;

@BindingMethods({
        @BindingMethod(type = HeightScalePicker.class, attribute = "maximumAcceptedSize", method = "setMaximumAcceptedSize"),
        @BindingMethod(type = HeightScalePicker.class, attribute = "typeOfUnits", method = "setTypeOfUnits"),
        @BindingMethod(type = HeightScalePicker.class, attribute = "mHeight", method = "setMHeight"),
})
@InverseBindingMethods({
        @InverseBindingMethod(type = HeightScalePicker.class, attribute = "mHeight", method = "getMHeight",event = "heightAttrChanged"),
})
public class HeightScalePickerBindingAdapter {

    @BindingAdapter(value = "heightAttrChanged")
    public static void onHeightChanged(HeightScalePicker view, final InverseBindingListener attrChange) {
        view.setOnHeightChangedListener(new HeightScalePicker.OnHeightChangedListener() {
            @Override
            public void OnHeightChanged(float height, String typeOfUnits) {
                if (attrChange != null) {
                    attrChange.onChange();
                }
            }
        });
    }

}