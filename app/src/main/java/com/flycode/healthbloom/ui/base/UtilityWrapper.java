package com.flycode.healthbloom.ui.base;


import com.flycode.healthbloom.data.models.User;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

@Data
public class UtilityWrapper {
    @Inject
    DatabaseDefinition database;
    @Inject
    @Named("default_user")
    User defaultUser;
}