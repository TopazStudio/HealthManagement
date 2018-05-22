package com.flycode.healthbloom.utils.BMI;

public class BMICalculator {

    private float Height;
    private float Weight;

    /**
     * Set height to be used
     * */
    public void setHeight(float value, String unit){
        switch (unit){
            case "cm(s)": //centimeters
                Height = UnitConverter.convert_from_centimeters_to_metres(value);
                break;
            case "ft": //feet
                Height = UnitConverter.convert_from_feat_to_metres(value);
                break;
            case "in(s)": //Inches
                Height = UnitConverter.convert_from_inches_to_metres(value);
                break;
        }
    }

    /**
     * Set weight to be used
     * */
    public void setWeight(float value, String unit){
        switch (unit){
            case "kg(s)": //kgs
                Weight = value;
                break;
            case "lb(s)": //pounds
                Weight = UnitConverter.convert_from_pounds_to_kilograms(value);
                break;
            case "st": //stone
                Height = UnitConverter.convert_from_stone_to_kilograms(value);
                break;
        }
    }

    //calculate bmi
    public float calculateBMI() {

        if (Height != 0 && Weight != 0) {

            //calulate bmi (kg/m^2)
            return Weight / (Height * Height);
        }else{
            return 0;
        }

    }
}