package com.flycode.healthbloom.data.models.CustomTypes;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

public class ObservableFieldString extends ObservableField<String> {
    public ObservableFieldString() {}
    public ObservableFieldString(String value) { super(value); }

    @Override
    @Nullable
    public String get() {
        return super.get();
    }

    @Override
    public void set(String value) {
        super.set(value);
    }
}

