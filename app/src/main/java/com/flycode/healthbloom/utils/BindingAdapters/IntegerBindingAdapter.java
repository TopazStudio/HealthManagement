package com.flycode.healthbloom.utils.BindingAdapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class IntegerBindingAdapter {
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

    /**
     * Convert an integer value to string value and set it on an
     * TextView view through the "android:text" attribute of the view.
     * */
    @BindingAdapter("android:text")
    public static void setText(TextView view, int value) {
        //Make sure its not the same value already in the EditText
        if (view.getText() != null
                && ( !view.getText().toString().isEmpty() )
                && Integer.parseInt(view.getText().toString()) != value) {
            view.setText(Integer.toString(value));
        }
    }

    /**
     * Get and convert the value of an TextView view to an integer and return it
     * from a "android:text" attribute of the view.
     * */
    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(TextView view) {
        //Make sure your not converting an empty or null value
        if (view.getText() != null && ( !view.getText().toString().isEmpty() )) {
            return Integer.parseInt(view.getText().toString());
        }
        return 0;
    }
}
