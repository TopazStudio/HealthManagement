package com.flycode.healthbloom.ui.exercise.exerciseView;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.Note;
import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.data.models.Tag;
import com.flycode.healthbloom.databinding.ExerciseViewBinding;
import com.flycode.healthbloom.ui.base.BaseView;
import com.flycode.healthbloom.ui.exercise.exerciseOverview.ExerciseOverviewActivity;
import com.flycode.healthbloom.ui.tags.TagsAdapter;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static com.flycode.healthbloom.ui.exercise.exerciseOverview.ExerciseOverviewActivity.STEPS_VIEW_EXTRA;

public class ExerciseViewActivity
        extends BaseView
        implements ExerciseViewContract.ExerciseViewView {

    @Inject
    ExerciseViewContract.ExerciseViewPresenter<ExerciseViewContract.ExerciseViewView> presenter;
    @Inject
    StepsUpperDetailsViewPagerAdapter stepsUpperDetailsViewPagerAdapter;
    @Inject
    Steps steps;
    @Inject @Named("view_tags")
    List<Tag> viewTags;
    @Inject @Named("edit_tags")
    List<Tag> editTags;
    @Inject
    Note note;
    @Inject
    TagsAdapter tagsAdapter;

    private ExerciseViewBinding exerciseViewBinding;
    private boolean isEditing = false;

    /*############################### ACTIVITY CALLBACKS ################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //BIND CONTENT
        exerciseViewBinding = DataBindingUtil.setContentView(this,R.layout.activity_exercise_view);
        exerciseViewBinding.setSteps(steps);
        exerciseViewBinding.setNote(note);

        //PRESENTER
        presenter.onAttach(this);

        //TOOLBAR
        setSupportActionBar((Toolbar) exerciseViewBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //INIT
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.exercise_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishAndGoTo(ExerciseOverviewActivity.class);
                return true;
            case R.id.action_edit:
                toggleMode();
                return true;
            case R.id.action_delete:
                presenter.onDelete();
                return true;
            case R.id.action_settings:
                return true;
            default:
                // Do nothing
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    /*############################### ACTIVITY LOGIC ################################*/

    private void init() {
        //FETCH DATA
        presenter.fetchAllTags();

        //Determine if its an update
        Intent i = getIntent();
        if(i.hasExtra(STEPS_VIEW_EXTRA)){
            mapSteps(new Gson()
                    .fromJson(i.getStringExtra(STEPS_VIEW_EXTRA),Steps.class));
            mapNote(steps.note);
            presenter.fetchStepsTags();
        }

        setupViewPager();
        setupChipsRecycler();
    }

    private void setupViewPager(){
        exerciseViewBinding.stepUpperDetailsViewPager.setOffscreenPageLimit(3);
        exerciseViewBinding.stepUpperDetailsViewPager.setAdapter(stepsUpperDetailsViewPagerAdapter);
    }

    private void setupChipsRecycler() {
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                .setMaxViewsInRow(4)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        exerciseViewBinding.chipsRecyclerView.setLayoutManager(chipsLayoutManager);
        exerciseViewBinding.chipsRecyclerView.addItemDecoration(
                new SpacingItemDecoration(getResources().getDimensionPixelOffset(R.dimen.tag_spacing),
                        getResources().getDimensionPixelOffset(R.dimen.tag_spacing)));
        exerciseViewBinding.chipsRecyclerView.setAdapter(tagsAdapter);
    }

    private void mapSteps(Steps steps){
        exerciseViewBinding.getSteps().id = steps.id;
        exerciseViewBinding.getSteps().Steps.set(steps.Steps.get());
        exerciseViewBinding.getSteps().Calories.set(steps.Calories.get());
        exerciseViewBinding.getSteps().CalorieUnits.set(steps.CalorieUnits.get());
        exerciseViewBinding.getSteps().Distance.set(steps.Distance.get());
        exerciseViewBinding.getSteps().DistanceUnits.set(steps.DistanceUnits.get());
        exerciseViewBinding.getSteps().StartTime.set(steps.StartTime.get());
        exerciseViewBinding.getSteps().EndTime.set(steps.EndTime.get());
        exerciseViewBinding.getSteps().note = steps.note;
    }

    private void mapNote(Note note){
        if (note != null){
            exerciseViewBinding.getNote().id = note.id;
            exerciseViewBinding.getNote().Content.set(note.Content.get());
        }
    }

    /**
     * Toggle the state of the Activity from Editing to viewing.
     * */
    @Override
    public void toggleMode(){
        if (!isEditing) {
            onEditing();
            isEditing = true;
        }
        else {
            onViewing();
            isEditing = false;
        }
    }


    private void onEditing(){
        exerciseViewBinding.edNoteView.setVisibility(View.GONE);
        exerciseViewBinding.edNoteEdit.setVisibility(View.VISIBLE);

        exerciseViewBinding.chipsRecyclerView.setVisibility(View.GONE);
        exerciseViewBinding.chipsInputEdit.setVisibility(View.VISIBLE);

        exerciseViewBinding.fabSave.setVisibility(View.VISIBLE);
    }

    private void onViewing(){
        exerciseViewBinding.edNoteView.setVisibility(View.VISIBLE);
        exerciseViewBinding.edNoteEdit.setVisibility(View.GONE);

        exerciseViewBinding.chipsRecyclerView.setVisibility(View.VISIBLE);
        exerciseViewBinding.chipsInputEdit.setVisibility(View.GONE);

        exerciseViewBinding.fabSave.setVisibility(View.GONE);
    }

    @Override
    public void setupTagsInput(){
        exerciseViewBinding.chipsInput.setFilterableList(editTags);
    }

    @Override
    public void addMultipleTags() {
        tagsAdapter.getTagList().clear();
        int startPosition = tagsAdapter.getTagList().size(); //Initial start position before adding.
        if (tagsAdapter.getTagList().addAll(viewTags)) {
            tagsAdapter.notifyItemRangeInserted(startPosition, tagsAdapter.getTagList().size());
        }
    }

    /*############################### UI CALLBACKS ################################*/

    /**
     * Save the changes done on the models.
     * This includes either updating or creating new records
     *
     **/
    public void onSave(View view){
        editTags = (List<Tag>) exerciseViewBinding.chipsInput.getSelectedChipList();
        presenter.onSave();
    }
}
