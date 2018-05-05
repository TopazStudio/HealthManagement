package com.flycode.healthbloom.ui.weight.WeightOverview;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.databinding.WeightActivityBinding;
import com.flycode.healthbloom.ui.base.BaseView;
import com.flycode.healthbloom.ui.weight.WeightOverview.BMIGraph.BMIGraphFragment;
import com.flycode.healthbloom.ui.weight.WeightOverview.WeightGraph.WeightGraphFragment;
import com.flycode.healthbloom.utils.DatabaseFaker;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class WeightOverviewActivity
        extends BaseView
        implements WeightOverviewContract.WeightView {

    @Inject
    GraphViewPagerAdapter graphViewPagerAdapter;
    @Inject
    EntryListAdapter entryListAdapter;
    @Inject
    WeightOverviewContract.WeightPresenter<WeightOverviewContract.WeightView> presenter;
    @Inject
    DatabaseFaker databaseFaker;

    private WeightActivityBinding binding;

    /**
     * Initialize data-binding on the activities layout and attach the presenter.
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_weight);
        setSupportActionBar((Toolbar) binding.toolbar);
        presenter.onAttach(this);
        init();
    }

    /**
     * Setup the graphs
     * */
    private void init(){
        setUpViewPager();
        setUpRecyclerView();

//        databaseFaker.fakeDefaultUser();
    }

    /**
     * Configures the view pager and assign the adapter
     *
     * */
    private void setUpViewPager(){
        graphViewPagerAdapter.addFragment(new WeightGraphFragment(),"Weight Graph");
        graphViewPagerAdapter.addFragment(new BMIGraphFragment(),"BMI Graph");

        binding.viewPager.setAdapter(graphViewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        //Set Custom TextView for TabLayout.
        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout_custom_view,null);
            Objects.requireNonNull(binding.tabLayout.getTabAt(i)).setCustomView(tv);

        }
        binding.viewPager.setOffscreenPageLimit(1);
    }

    /**
     * Configures the recycler view and calls for data fetching from the
     * activities presenter
     *
     * */
    private void setUpRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        //CONFIGURE
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(layoutManager);

        //FETCH DATA
        //TODO: show loading while data is being fetched.
        presenter.getWeightMeasurements();
    }

    /**
     * Called after weight measurement models are retrieved from the database.
     * The recycler view is then initialized with the data.
     *
     * */
    public void setWeightEntries(List<WeightMeasurement> weightEntries){
        //ENTER DATA
        entryListAdapter.setListItems(weightEntries);
        entryListAdapter.setContext(this);

        //ASSIGN ADAPTER
        if (binding.recyclerView.getAdapter() != null){
            binding.recyclerView.swapAdapter(entryListAdapter,true);
        }
        binding.recyclerView.setAdapter(entryListAdapter);
    }

    /**
     * Called when a weight entry trigger is fired either from a fab button or other triggers
     * */
    public void onAddWeight(View view){
        presenter.addWeight();
    }

    public void onUpdateWeight(View view){
        presenter.onUpdateWeight(null);
    }

    /**
     * On returning results from Child activity.
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == currentRequestCode) && (resultCode == 1)){
            //Refresh the graphs and entries
            //TODO:Refresh graphs
            showMessage("Saved Successfully");
            presenter.getWeightMeasurements();
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
