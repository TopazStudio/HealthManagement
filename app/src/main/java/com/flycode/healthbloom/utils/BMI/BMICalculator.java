package com.flycode.healthbloom.utils.BMI;

public class BMICalculator {

    private float Height;
    private float Weight;

    /**
     * Set height to be used
     * */
    public void setHeight(int value, int unit){
        switch (unit){
            case 0: //meters
                Height = value;
                break;
            case 1: //Inches
                Height = UnitConverter.convert_from_inches_to_metres(value);
                break;
            case 2: //feet
                Height = UnitConverter.convert_from_feat_to_metres(value);
                break;
        }
    }

    /**
     * Set weight to be used
     * */
    public void setWeight(int value, int unit){
        switch (unit){
            case 0: //kgs
                Height = value;
                break;
            case 1: //pounds
                Height = UnitConverter.convert_from_pounds_to_kilograms(value);
                break;
        }
    }

    //calculate bmi
    public float calculateBMI() {

        if (Height != 0 && Weight != 0) {

            //calulate bmi
            return Weight / (Height * Height);
        }else{
            return 0;
        }

    }
}