package com.flycode.healthbloom.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XAxisDateValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Date date = new Date();
        date.setTime((long) value);
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}
