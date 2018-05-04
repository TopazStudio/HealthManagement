package com.flycode.healthbloom.ui.weight.WeightEntry;

import android.content.Context;

import com.flycode.healthbloom.data.models.Note;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.data.models.WeightMeasurement;

import java.util.ArrayList;
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
    provideWeightEntryPresenter(WeightMeasurement weightMeasurement,Note note,List<Tag> tags) {
        WeightEntryPresenter presenter = new WeightEntryPresenter<>();
        presenter.setWeightMeasurement(weightMeasurement);
        presenter.setNote(note);
        presenter.setTags(tags);
        return presenter;
    }

    @Provides
    WeightMeasurement provideWeightMeasurement(){
        return new WeightMeasurement();
    }

    @Provides
    Note provideNote(){
        return new Note();
    }

    @Provides
    List<Tag> provideTags(){
        return new ArrayList<>();
    }
}
