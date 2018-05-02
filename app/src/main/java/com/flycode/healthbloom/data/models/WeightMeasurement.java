package com.flycode.healthbloom.data.models;

import android.databinding.ObservableFloat;

import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableDate;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFloatConverter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import lombok.Data;

/**
 * User model used to move and persist data
 * associated with weight entity.
 *
 * */
@Data
@Table(database = Database.class, name = "weight_measurement" )
public class WeightMeasurement extends BaseModel{
    @PrimaryKey(autoincrement = true)
    @Column()
    public int id;

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat Weight = new ObservableFloat();

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat Height = new ObservableFloat();

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat BMI = new ObservableFloat();

//    @Column(typeConverter = ObservableDateConverter.class)
    public ObservableDate Date = new ObservableDate();
}
