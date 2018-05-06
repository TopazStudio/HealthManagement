package com.flycode.healthbloom.utils.BMI;

public class Height_Weight_Converter {
    //convert height
    //inches to metres
    public float convert_from_inches_to_metres(String value){
        //1 inch = 0.0254 metres
        String convert = "0.0254";
        return (Float.parseFloat(convert)*Float.parseFloat(value));
    }

    //feet to metres
    public float convert_from_feat_to_metres(String value){
        //1 feet = 0.3048 metres
        String convert = "0.3048";
        return (Float.parseFloat(convert)*Float.parseFloat(value));
    }

    //convert weight from pounds to kilos
    public float convert_from_pounds_to_kilograms(String value){
        //1 pound = 0.454 kilogram
        String convert = "0.454";
        return (Float.parseFloat(convert)*Float.parseFloat(value));
    }
}
