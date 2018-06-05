package com.flycode.healthbloom.data.models;

import android.databinding.ObservableInt;

import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableDate;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableFieldString;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableDateConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFieldStringConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableIntConverter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = Database.class, name = "steps" )
@ManyToMany(referencedTable = Tag.class)
public class Steps extends BaseModel{
    @PrimaryKey(autoincrement = true)
    @Column()
    public int id;

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt Steps = new ObservableInt();

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt Calories = new ObservableInt();

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString CalorieUnits = new ObservableFieldString("kcal(s)");

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt Distance = new ObservableInt();

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString DistanceUnits = new ObservableFieldString("km(s)");

    @Column(typeConverter = ObservableDateConverter.class)
    public ObservableDate StartTime = new ObservableDate();

    @Column(typeConverter = ObservableDateConverter.class)
    public ObservableDate EndTime = new ObservableDate();

    //RELATIONSHIPS
    @ForeignKey(saveForeignKeyModel = true)
    public Note note;
}
