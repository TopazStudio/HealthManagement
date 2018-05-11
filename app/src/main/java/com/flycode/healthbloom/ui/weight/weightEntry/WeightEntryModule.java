package com.flycode.healthbloom.ui.weight.weightEntry;

import android.content.Context;

import com.flycode.healthbloom.data.models.Note;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.di.scope.PerActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class WeightEntryModule {
    @Provides
    Context provideWeightEntryActivityContext(WeightEntryActivity WeightEntryActivity){
        return WeightEntryActivity;
    }

    @Provides
    WeightEntryContract.WeightEntryPresenter<WeightEntryContract.WeightEntryView>
    provideWeightEntryPresenter(WeightMeasurement weightMeasurement,
                                Note note,
                                List<Tag> tags,
                                Calendar entryCalendar) {
        WeightEntryPresenter<WeightEntryContract.WeightEntryView> presenter = new WeightEntryPresenter<>();
        presenter.setWeightMeasurement(weightMeasurement);
        presenter.setNote(note);
        presenter.setTags(tags);
        presenter.setEntryCalendar(entryCalendar);
        return presenter;
    }

    @Provides
    @PerActivity
    WeightMeasurement provideWeightMeasurement(){
        return new WeightMeasurement();
    }

    @Provides
    @PerActivity
    Note provideNote(){
        return new Note();
    }

    @Provides
    @PerActivity
    List<Tag> provideTags(){
        return new ArrayList<>();
    }

    @Provides
    @PerActivity
    Calendar provideEntryCalendar(){
        return Calendar.getInstance();
    }
}
