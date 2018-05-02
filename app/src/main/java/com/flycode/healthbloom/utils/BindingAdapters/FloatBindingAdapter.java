package com.flycode.healthbloom.utils.BindingAdapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class FloatBindingAdapter {
    /**
     * Convert an float value to string value and set it on an
     * EditText view through the "android:text" attribute of the view.
     * */
    @BindingAdapter("android:text")
    public static void setText(EditText view, float value) {
        //Make sure its not the same value already in the EditText
        if (view.getText() != null && ( !view.getText().toString().isEmpty() )) {
            //if there is a value already in the view check if its the same
            float a = 0f;

            try{
                a = Float.parseFloat(view.getText().toString());
            }catch (Exception e){
                Log.e("FloatParseError",e.getMessage());
            }

            if (a != value)
                view.setText(Float.toString(value));
            else return;
        } else
            //if no value exists then just add the new value
            view.setText(Float.toString(value));
    }

    /**
     * Get and convert the value of an EditText view to an float and return it
     * from a "android:text" attribute of the view.
     * */
    @InverseBindingAdapter(attribute = "android:text")
    public static float getText(EditText view) {
        //Make sure your not converting an empty or null value
        if (view.getText() != null && ( !view.getText().toString().isEmpty() )) {
            try{
                return Float.parseFloat(view.getText().toString());
            }catch (Exception e){
                Log.e("FloatParseError",e.getMessage());
            }
        }
        return 0;
    }

    /**
     * Convert an float value to string value and set it on an
     * TextView view through the "android:text" attribute of the view.
     * */
    @BindingAdapter("android:text")
    public static void setText(TextView view, float value) {
        //Make sure its not the same value already in the TextView
        if (view.getText() != null && ( !view.getText().toString().isEmpty() )) {
            //if there is a value already in the view check if its the same
            float a = 0f;

            try{
                a = Float.parseFloat(view.getText().toString());
            }catch (Exception e){
                Log.e("FloatParseError",e.getMessage());
            }

            if (a != value)
                view.setText(Float.toString(value));
            else return;
        } else
                //if no value exists then just add the new value
                view.setText(Float.toString(value));
    }

    /**
     * Get and convert the value of an TextView view to an float and return it
     * from a "android:text" attribute of the view.
     * */
    @InverseBindingAdapter(attribute = "android:text")
    public static float getText(TextView view) {
        //Make sure your not converting an empty or null value
        if (view.getText() != null && ( !view.getText().toString().isEmpty() )) {
            try{
                return Float.parseFloat(view.getText().toString());
            }catch (Exception e){
                Log.e("FloatParseError",e.getMessage());
            }
        }
        return 0;
    }
}
