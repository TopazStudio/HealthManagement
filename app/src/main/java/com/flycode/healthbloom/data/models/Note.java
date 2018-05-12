package com.flycode.healthbloom.data.models;

import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableFieldString;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFieldStringConverter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = Database.class, name = "notes" )
public class Note extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column
    public int id;

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString Content = new ObservableFieldString();
}
