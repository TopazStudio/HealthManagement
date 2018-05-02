package com.flycode.healthbloom.data.models.CustomTypes;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import java.util.Date;

public class ObservableDate extends ObservableField<Date> {
    public ObservableDate() {}
    public ObservableDate(Date value) { super(value); }

    @Override
    @Nullable
    public Date get() {
        return super.get();
    }

    @Override
    public void set(Date value) {
        super.set(value);
    }
}
