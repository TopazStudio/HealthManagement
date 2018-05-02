package com.flycode.healthbloom.utils;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

public class LineDataSetFix extends LineDataSet {

    public LineDataSetFix(List<Entry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public Entry getEntryForIndex(int index) {
        if (!(mValues.size() == index)){
            return mValues.isEmpty() ? null : mValues.get(index);
        } else {
            return mValues.isEmpty() ? null : mValues.get(index - 1);
        }
    }
}
