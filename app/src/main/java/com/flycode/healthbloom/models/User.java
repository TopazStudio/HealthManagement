package com.flycode.healthbloom.models;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import lombok.Data;

@Data
public class User {
    public final ObservableInt Id = new ObservableInt();
    public final ObservableField<String> Fullname = new ObservableField<>();
    public final ObservableBoolean isMale = new ObservableBoolean();
    public final ObservableBoolean isFemale = new ObservableBoolean();
    public final ObservableInt Age = new ObservableInt();
    public final ObservableInt InitWeight = new ObservableInt();
    public final ObservableInt InitHeight = new ObservableInt();
}
