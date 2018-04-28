package com.flycode.healthbloom.di.module;

import com.flycode.healthbloom.data.db.Database;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    public DatabaseDefinition provideDatabaseDefinition(){
        return FlowManager.getDatabase(Database.class);
    }

}
