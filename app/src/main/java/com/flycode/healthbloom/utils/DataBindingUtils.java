package com.flycode.healthbloom.utils;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.graphics.Typeface;
import android.widget.EditText;
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

    /**
     * Convert an integer value to string value and set it on an
     * EditText view through the "android:text" attribute of the view.
     * */
    @BindingAdapter("android:text")
    public static void setText(EditText view, int value) {
        //Make sure its not the same value already in the EditText
        if (view.getText() != null
                && ( !view.getText().toString().isEmpty() )
                && Integer.parseInt(view.getText().toString()) != value) {
            view.setText(Integer.toString(value));
        }
    }

    /**
     * Get and convert the value of an EditText view to an integer and return it
     * from a "android:text" attribute of the view.
     * */
    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(EditText view) {
        //Make sure your not converting an empty or null value
        if (view.getText() != null && ( !view.getText().toString().isEmpty() )) {
            return Integer.parseInt(view.getText().toString());
        }
        return 0;
    }
}
