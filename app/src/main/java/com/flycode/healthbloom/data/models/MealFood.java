package com.flycode.healthbloom.data.models;

import com.flycode.healthbloom.data.models.TypeConverters.ObservableIntConverter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import lombok.Data;

@Data
@Table(database = Database.class, name = "MealFood")
public class MealFood extends BaseModel{
    @PrimaryKey(autoincrement = true)
    @Column()
    public int MealFoodId;

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableIntConverter FoodId = new ObservableIntConverter();

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableIntConverter Quantity = new ObservableIntConverter();

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableIntConverter MealId = new ObservableIntConverter();



}
