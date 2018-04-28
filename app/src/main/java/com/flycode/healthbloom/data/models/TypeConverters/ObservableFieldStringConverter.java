package com.flycode.healthbloom.data.models.TypeConverters;

import com.flycode.healthbloom.data.models.CustomTypes.ObservableFieldString;
import com.raizlabs.android.dbflow.converter.TypeConverter;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class ObservableFieldStringConverter extends TypeConverter<String, ObservableFieldString> {

    @Override
    public String getDBValue(ObservableFieldString model) {
        return model.get();
    }

    @Override
    public ObservableFieldString getModelValue(String data) {
        return new ObservableFieldString(data);
    }
}
