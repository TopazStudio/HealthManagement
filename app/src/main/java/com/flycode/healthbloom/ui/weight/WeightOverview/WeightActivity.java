package com.flycode.healthbloom.ui.weight.WeightOverview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.databinding.WeightActivityBinding;
import com.flycode.healthbloom.ui.base.BaseView;
import com.flycode.healthbloom.ui.weight.WeightOverview.BMIGraph.BMIGraphFragment;
import com.flycode.healthbloom.ui.weight.WeightOverview.WeightGraph.WeightGraphFragment;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class WeightActivity
        extends BaseView
        implements WeightContract.WeightView {

    @Inject
    GraphViewPagerAdapter graphViewPagerAdapter;
    @Inject
    EntryListAdapter entryListAdapter;
    @Inject
    WeightContract.WeightPresenter<WeightContract.WeightView> presenter;

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
        binding.recyclerView.setAdapter(entryListAdapter);
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
