package com.flycode.healthbloom.data.models;

import android.databinding.ObservableFloat;

import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableDate;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableFieldString;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableDateConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFieldStringConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFloatConverter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
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
@ManyToMany(referencedTable = Tag.class)
public class WeightMeasurement extends BaseModel{
    @PrimaryKey(autoincrement = true)
    @Column()
    public int id;

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat Weight = new ObservableFloat();

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString WeightUnits = new ObservableFieldString("kg(s)");

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat Height = new ObservableFloat();

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString HeightUnits = new ObservableFieldString("m(s)");

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat BMI = new ObservableFloat();

    @Column(typeConverter = ObservableDateConverter.class)
    public ObservableDate Date = new ObservableDate();

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString PhotoLocation = new ObservableFieldString();

    //RELATIONSHIPS
    @ForeignKey(saveForeignKeyModel = true)
    public Note note;
}
