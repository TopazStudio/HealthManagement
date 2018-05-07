package com.flycode.healthbloom.data.models;

import com.flycode.healthbloom.data.models.CustomTypes.ObservableDate;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableFieldString;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableDateConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFieldStringConverter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import lombok.Data;

@Data
@Table(database = Database.class, name="Meals")
public class Meals extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column()
    public int MealId;

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString Type = new ObservableFieldString();

    @Column(typeConverter = ObservableDateConverter.class)
    public ObservableDate Date = new ObservableDate();

}
