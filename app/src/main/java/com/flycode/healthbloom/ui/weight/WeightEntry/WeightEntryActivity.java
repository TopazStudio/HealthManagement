package com.flycode.healthbloom.ui.weight.WeightEntry;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.Note;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.databinding.WeightEntryBinding;
import com.flycode.healthbloom.ui.base.BaseView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

//TODO: catch the back button and warn the user of losing data.
public class WeightEntryActivity
        extends BaseView
        implements WeightEntryContract.WeightEntryView  {

    @Inject
    WeightEntryContract.WeightEntryPresenter<WeightEntryContract.WeightEntryView> presenter;
    @Inject
    WeightMeasurement weightMeasurement;
    @Inject
    List<Tag> tags;
    @Inject
    Note note;

    private WeightEntryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weight_entry);
        binding.setWeightMeasurement(weightMeasurement);
        binding.setNote(note);

        presenter.onAttach(this);

        setSupportActionBar((Toolbar) binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init(){
        presenter.fetchTags();

        //TODO: send the whole weightMeasurement instead of having to requery the database
        //Determine if its a new entry
        Intent i = getIntent();
        if(i.hasExtra("id")){
            presenter.fetchWeightEntry(i.getIntExtra("id",1));
        }
    }

    @Override
    public void setupTagsInput(){
        binding.chipsInput.setFilterableList(tags);
    }

    public void onSave(View view){
        tags = (List<Tag>) binding.chipsInput.getSelectedChipList();
        presenter.onSave();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
