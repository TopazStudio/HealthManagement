package com.flycode.healthbloom.ui.exercise.exerciseOverview;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.Steps;
import com.flycode.healthbloom.databinding.ExerciseOverviewBinding;
import com.flycode.healthbloom.ui.base.BaseViewWithNav;
import com.flycode.healthbloom.utils.ShouldScrolledBehaviour;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class ExerciseOverviewActivity
        extends BaseViewWithNav
        implements ExerciseOverviewContract.ExerciseOverviewView {

    @Inject
    ExerciseEntryListAdapter exerciseEntryListAdapter;
    @Inject
    ExerciseOverviewContract.ExerciseOverviewPresenter
            <ExerciseOverviewContract.ExerciseOverviewView> presenter;

    private ExerciseOverviewBinding exerciseOverviewBinding;
    public static final int ADD_STEPS_REQUEST_CODE = 1;
    public static final int VIEW_STEPS_REQUEST_CODE = 2;
    public static final String STEPS_VIEW_EXTRA = "steps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ADD CONTENT
        exerciseOverviewBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_exercise_overview,null,false);
        baseActivityBinding.contentFrame.addView(exerciseOverviewBinding.getRoot());

        //PRESENTER
        presenter.onAttach(this);

        //TOOLBAR
        setSupportActionBar((Toolbar) exerciseOverviewBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //INIT
        init();
    }

    private void init(){
        //FETCH DATA
        presenter.getSteps();
    }

    /**
     * Called after steps models are retrieved from the database.
     * The recycler view is then initialized with the data.
     *
     * */
    public void setStepsEntries(List<Steps> stepsEntries){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //CONFIGURE
        exerciseOverviewBinding.recyclerView.setHasFixedSize(true);
        exerciseOverviewBinding.recyclerView.setLayoutManager(layoutManager);

        //ENTER DATA
        exerciseEntryListAdapter.setListItems(stepsEntries);
        exerciseEntryListAdapter.setContext(this);

        //SET ON_CARD_VIEW_CLICKED_LISTENER
        exerciseEntryListAdapter.setOnCardViewClicked(new ExerciseEntryListAdapter.OnCardViewClicked() {
            @Override
            public void onClicked(Steps steps) {
                presenter.onViewSteps(steps);
            }
        });

        // SET NO SCROLLING BEHAVIOUR WHEN ITEMS ARE LITTLE
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                exerciseOverviewBinding.recyclerView.getLayoutParams();
        params.setBehavior(new ShouldScrolledBehaviour(layoutManager,exerciseEntryListAdapter));
        exerciseOverviewBinding.recyclerView.setLayoutParams(params);

        //ASSIGN ADAPTER
        if (exerciseOverviewBinding.recyclerView.getAdapter() != null){
            exerciseOverviewBinding.recyclerView.swapAdapter(exerciseEntryListAdapter,true);
        }
        exerciseOverviewBinding.recyclerView.setAdapter(exerciseEntryListAdapter);
    }

    /**
     * Called when a weight entry trigger is fired either from a fab button or other triggers
     * */
    public void onAddSteps(View view){
        presenter.addSteps();
    }

    /**
     * On returning results from Child activity.
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == ADD_STEPS_REQUEST_CODE) && (resultCode == RESULT_OK)){
            //Refresh the graphs and entries
            showMessage("Saved Successfully");
            presenter.getSteps();
        }else {
            showError("Sorry. Something went wrong. Please try again.");
        }
    }

    /**
     * Detaches the presenter from the activity.
     *
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
