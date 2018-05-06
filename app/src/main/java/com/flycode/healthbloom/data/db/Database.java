package com.flycode.healthbloom.data.db;

@com.raizlabs.android.dbflow.annotation.Database(
        name = Database.NAME,
        version = Database.VERSION,
        foreignKeyConstraintsEnforced = true)
public class Database {
    public static final String NAME = "Health_Upgrade";
    public static final int VERSION = 2;
}
