package com.flycode.healthbloom.data.models.TypeConverters;

import android.databinding.ObservableFloat;

import com.raizlabs.android.dbflow.converter.TypeConverter;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class ObservableFloatConverter extends TypeConverter<Float, ObservableFloat> {
    @Override
    public Float getDBValue(ObservableFloat model) {
        return model.get();
    }

    @Override
    public ObservableFloat getModelValue(Float data) {
        return new ObservableFloat(data);
    }
}
