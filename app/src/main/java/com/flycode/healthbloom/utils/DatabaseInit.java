package com.flycode.healthbloom.utils;

import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;

public class DatabaseInit {
    private static List<WeightMeasurement> weightMeasermentList = new ArrayList<>();

    /**
     * Write fake data into database.
     * */
    public static void init(DatabaseDefinition database) throws ParseException {
        Faker faker = new Faker();

        for (int i = 0; i < 10; i++){
            WeightMeasurement w = new WeightMeasurement();
            w.Weight.set((float) faker.number.between(60.34,100.00));
            w.Height.set((float) faker.number.between(60.34,100.00));
            w.BMI.set((float) faker.number.between(10.09,30.9));
            w.Date.set(faker.date.between(new SimpleDateFormat("dd-M-yyyy").parse("12-11-2016")
                    ,new SimpleDateFormat("dd-M-yyyy").parse("12-11-2018")));
            weightMeasermentList.add(w);
        }

        database.executeTransaction(
                FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(WeightMeasurement.class))
                .addAll(weightMeasermentList)
                .build());
    }
}
