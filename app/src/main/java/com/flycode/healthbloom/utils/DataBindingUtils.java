package com.flycode.healthbloom.utils;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Helper methods for dealing with data-binding layouts
 * */
public class DataBindingUtils {

    //TODO: figure out where the "fonts/" folder should be located.
    /**
     * Sets fonts on TextView textView from the
     * <br>"fonts/"</br> folder*
     * */
    @BindingAdapter("app:font")
    public static void setFont(TextView textView, String fontName){
        Typeface typeface = Typeface.createFromAsset(
                textView.getContext().getAssets(),"fonts/" +fontName);
        textView.setTypeface(typeface);
    }
}
