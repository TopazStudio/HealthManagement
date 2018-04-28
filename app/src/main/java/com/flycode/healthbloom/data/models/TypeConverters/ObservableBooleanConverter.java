package com.flycode.healthbloom.data.models.TypeConverters;

import android.databinding.ObservableBoolean;

import com.raizlabs.android.dbflow.converter.TypeConverter;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class ObservableBooleanConverter extends TypeConverter<Boolean, ObservableBoolean> {


    @Override
    public Boolean getDBValue(ObservableBoolean model) {
        return model.get();
    }

    @Override
    public ObservableBoolean getModelValue(Boolean data) {
        return new ObservableBoolean(data);
    }
}
