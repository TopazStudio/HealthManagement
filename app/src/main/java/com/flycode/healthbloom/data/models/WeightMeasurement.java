package com.flycode.healthbloom.data.models;

import android.databinding.ObservableFloat;

import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.CustomTypes.ObservableDate;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableDateConverter;
import com.flycode.healthbloom.data.models.TypeConverters.ObservableFloatConverter;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

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

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat Height = new ObservableFloat();

    @Column(typeConverter = ObservableFloatConverter.class)
    public ObservableFloat BMI = new ObservableFloat();

    @Column(typeConverter = ObservableDateConverter.class)
    public ObservableDate Date = new ObservableDate();

    @Column
    public String PhotoLocation;

    //RELATIONSHIPS
    public List<Note> notes;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "notes")
    public List<Note> getMyNotes() {
        if (notes == null || notes.isEmpty()) {

            notes = SQLite.select()
                    .from(Note.class)
                    .where(Note_Table.weightMeasurement_id.eq(id))
                    .queryList();

        }
        return notes;
    }
}
