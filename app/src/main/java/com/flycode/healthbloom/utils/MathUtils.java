package com.flycode.healthbloom.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}
