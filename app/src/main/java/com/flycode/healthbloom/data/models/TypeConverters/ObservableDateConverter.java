package com.flycode.healthbloom.data.models.TypeConverters;

import com.flycode.healthbloom.data.models.CustomTypes.ObservableDate;
import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.util.Date;
import java.util.Objects;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class ObservableDateConverter extends TypeConverter<Long, ObservableDate> {
    @Override
    public Long getDBValue(ObservableDate model) {
        return model == null ? null : Objects.requireNonNull(model.get()).getTime();
    }

    @Override
    public ObservableDate getModelValue(Long data) {
        return data == null ? null : new ObservableDate(new Date(data));
    }
}
