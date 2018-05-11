package com.flycode.healthbloom.di.module;

import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.User;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    public DatabaseDefinition provideDatabaseDefinition(){
        return FlowManager.getDatabase(Database.class);
    }

/*    @Provides
    @Singleton
    public DatabaseFaker provideDatabaseFaker(DatabaseDefinition database){
        return new DatabaseFaker(database);
    }*/

    @Provides
    @Singleton
    @Named("default_user")
    public User provideDefaultUser(){
        return SQLite.select()
                .from(User.class)
                .querySingle();
    }

}
