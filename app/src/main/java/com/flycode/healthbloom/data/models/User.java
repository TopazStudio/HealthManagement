package com.flycode.healthbloom.data.models;

import android.databinding.ObservableInt;

import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableFieldString;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFieldStringConverter;
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
    @PrimaryKey
    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt id = new ObservableInt();

    //Removed generic type on ObservableField due to dbflow not working well with
    //them.
    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString Fullname = new ObservableFieldString();

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString Gender = new ObservableFieldString();

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt Age = new ObservableInt();

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt InitWeight = new ObservableInt();

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt InitHeight = new ObservableInt();
}
