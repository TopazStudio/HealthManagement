package com.flycode.healthbloom.utils;

import android.graphics.Color;

import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;

public class DatabaseFaker {
    private Faker faker = new Faker();
    private DatabaseDefinition database;

    public DatabaseFaker(DatabaseDefinition database){
        this.database = database;
    }

    public void fakeWeightMeasurements() throws ParseException {
        List<WeightMeasurement> weightMeasurementList = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            WeightMeasurement w = new WeightMeasurement();
            w.Weight.set((float) faker.number.between(60.34,100.00));
            w.Height.set((float) faker.number.between(60.34,100.00));
            w.BMI.set((float) faker.number.between(10.09,30.9));
            w.Date.set(faker.date.between(new SimpleDateFormat("dd-M-yyyy").parse("12-11-2016")
                    ,new SimpleDateFormat("dd-M-yyyy").parse("12-11-2018")));
            weightMeasurementList.add(w);
        }

        database.executeTransaction(
                FastStoreModelTransaction
                        .insertBuilder(FlowManager.getModelAdapter(WeightMeasurement.class))
                        .addAll(weightMeasurementList)
                        .build());
    }

    public void fakeDefaultUser(){

        User user = new User();
        user.Fullname.set(faker.name.name());
        user.Age.set(faker.number.between(12,50));
        user.Gender.set("male");
        user.InitHeight.set(faker.number.between(1,5));
        user.InitWeight.set(faker.number.between(30,100));


        database.executeTransaction(
                FastStoreModelTransaction
                        .insertBuilder(FlowManager.getModelAdapter(User.class))
                        .add(user)
                        .build());
    }

    public void fakeTags(){
        List<Tag> tags = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            Tag tag = new Tag();
            tag.Name.set(faker.lorem.characters(6));
            tag.Description.set(faker.lorem.paragraph(2));
            tag.Color.set(faker.number.between(Color.WHITE,Color.BLACK));
            tags.add(tag);
        }

        database.executeTransaction(
                FastStoreModelTransaction
                        .insertBuilder(FlowManager.getModelAdapter(Tag.class))
                        .addAll(tags)
                        .build());
    }
}
