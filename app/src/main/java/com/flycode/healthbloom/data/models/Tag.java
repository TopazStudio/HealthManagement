package com.flycode.healthbloom.data.models;

import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableFieldString;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFieldStringConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableIntConverter;
import com.pchmn.materialchips.model.ChipInterface;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = Database.class, name = "tags" )
public class Tag extends BaseModel implements ChipInterface {
    @PrimaryKey(autoincrement = true)
    @Column
    public int id;

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString Name = new ObservableFieldString();

    @Column(typeConverter = ObservableFieldStringConverter.class)
    public ObservableFieldString Description = new ObservableFieldString();

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt PrimaryColor = new ObservableInt();

    @Column(typeConverter = ObservableIntConverter.class)
    public ObservableInt SecondaryColor = new ObservableInt();

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public Uri getAvatarUri() {
        return null;
    }

    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }

    @Override
    public String getLabel() {
        return Name.get();
    }

    @Override
    public String getInfo() {
        return Description.get();
    }
}
