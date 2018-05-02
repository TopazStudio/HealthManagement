package com.flycode.healthbloom.data.models.TypeConverters;

import com.flycode.healthbloom.data.models.CustomTypes.ObservableDate;
import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.util.Date;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class ObservableDateConverter extends TypeConverter<Date, ObservableDate> {
    @Override
    public Date getDBValue(ObservableDate model) {
        return model.get();
    }

    @Override
    public ObservableDate getModelValue(Date data) {
        return new ObservableDate(data);
    }
}
