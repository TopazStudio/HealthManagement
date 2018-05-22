package com.flycode.healthbloom.utils.BMI;

public class UnitConverter {
    //convert height
    //inches to metres
    public static float convert_from_inches_to_metres(float value){
        //1 inch = 0.0254 metres
        float convert = 0.0254f;
        return (convert * value);
    }

    //feet to metres
    public static float convert_from_feat_to_metres(float value){
        //1 feet = 0.3048 metres
        float convert = 0.3048f;
        return (convert * value);
    }

    //convert weight from pounds to kilos
    public static float convert_from_pounds_to_kilograms(float value){
        //1 pound = 0.454 kilogram
        float convert = 0.453592f;
        return (convert * value);
    }

    public static float convert_from_ounce_to_kilograms(float value) {
        float covert = 0.028350f;
        return (covert * value);
    }

    public static float convert_from_centimeters_to_metres(float value) {
        float covert = 100f;
        return (value / covert);
    }

    public static float convert_from_stone_to_kilograms(float value) {
        float covert = 6.35029f;
        return (value * covert);
    }
}
