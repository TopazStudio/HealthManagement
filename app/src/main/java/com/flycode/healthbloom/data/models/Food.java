package com.flycode.healthbloom.data.models;

import com.flycode.healthbloom.data.models.CustomTypes.ObservableFieldString;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableBooleanConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFieldStringConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFloatConverter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import lombok.Data;

/*
* Food model used to persist data
* */
@Data
@Table(database = Database.class, name="foot")
public class Food extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column()
    public int id;

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString FoodName = new ObservableFieldString();

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloatConverter Carb = new ObservableFloatConverter();

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloatConverter Fat = new ObservableFloatConverter();

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloatConverter Protein = new ObservableFloatConverter();

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloatConverter Calories = new ObservableFloatConverter();

}
