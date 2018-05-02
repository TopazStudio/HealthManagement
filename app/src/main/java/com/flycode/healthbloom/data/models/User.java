package com.flycode.healthbloom.data.models;

import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;

import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableFieldString;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFieldStringConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFloatConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableIntConverter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * User model used to move and persist data
 * associated with user entity.
 *
 * */
@Table(database = Database.class, name = "user" )
public class User extends BaseModel{
    @PrimaryKey(autoincrement = true)
    @Column()
    public int id;

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString Fullname = new ObservableFieldString();

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString Gender = new ObservableFieldString();

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt Age = new ObservableInt();

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat InitWeight = new ObservableFloat();

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat InitHeight = new ObservableFloat();
}
