package com.flycode.healthbloom.data.models.TypeConverters;

import android.databinding.ObservableInt;

import com.raizlabs.android.dbflow.converter.TypeConverter;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class ObservableIntConverter extends TypeConverter<Integer, ObservableInt> {

    @Override
    public Integer getDBValue(ObservableInt model) {
        return model.get();
    }

    @Override
    public ObservableInt getModelValue(Integer data) {
        return new ObservableInt(data);
    }
}
