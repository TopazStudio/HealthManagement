package com.flycode.healthbloom.ui.weight.weightOverview;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.data.models.WeightMeasurement;
import com.flycode.healthbloom.databinding.WeightActivityBinding;
import com.flycode.healthbloom.ui.base.BaseViewWithNav;
import com.flycode.healthbloom.ui.weight.weightOverview.BMIGraph.BMIGraphFragment;
import com.flycode.healthbloom.ui.weight.weightOverview.WeightGraph.WeightGraphFragment;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class WeightOverviewActivity
        extends BaseViewWithNav
        implements WeightOverviewContract.WeightView {

    @Inject
    GraphViewPagerAdapter graphViewPagerAdapter;
    @Inject
    EntryListAdapter entryListAdapter;
    @Inject
    WeightOverviewContract.WeightPresenter<WeightOverviewContract.WeightView> presenter;

    private WeightActivityBinding weightActivityBinding;

    /**
     * Initialize data-weightActivityBinding on the activities layout and attach the presenter.
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ADD CONTENT
        weightActivityBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_weight_overview,null,false);
        baseActivityBinding.contentFrame.addView(weightActivityBinding.getRoot());

        //PRESENTER
        presenter.onAttach(this);

        //TOOLBAR
        setSupportActionBar((Toolbar) weightActivityBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //INIT
        init();
    }

    /**
     * Setup the graphs
     * */
    private void init(){
        //FETCH DATA
        presenter.getWeightMeasurements();

        setUpViewPager();
    }

    /**
     * Configures the view pager and assign the adapter
     *
     * */
    private void setUpViewPager(){
        graphViewPagerAdapter.addFragment(new WeightGraphFragment(),"Weight Graph");
        graphViewPagerAdapter.addFragment(new BMIGraphFragment(),"BMI Graph");

        weightActivityBinding.viewPager.setAdapter(graphViewPagerAdapter);
        weightActivityBinding.tabLayout.setupWithViewPager(weightActivityBinding.viewPager);

        //Set Custom TextView for TabLayout.
        for (int i = 0; i < weightActivityBinding.tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout_custom_view,null);
            Objects.requireNonNull(weightActivityBinding.tabLayout.getTabAt(i)).setCustomView(tv);

        }
        weightActivityBinding.viewPager.setOffscreenPageLimit(1);
    }

    /**
     * Called after weight measurement models are retrieved from the database.
     * The recycler view is then initialized with the data.
     *
     * */
    public void setWeightEntries(List<WeightMeasurement> weightEntries){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //CONFIGURE
        weightActivityBinding.recyclerView.setHasFixedSize(true);
        weightActivityBinding.recyclerView.setLayoutManager(layoutManager);

        //ENTER DATA
        entryListAdapter.setListItems(weightEntries);
        entryListAdapter.setContext(this);

        //SET ON_CARD_VIEW_CLICKED_LISTENER
        entryListAdapter.setOnCardViewClicked(new EntryListAdapter.OnCardViewClicked() {
            @Override
            public void onClicked(WeightMeasurement weightMeasurement) {
                presenter.onUpdateWeight(weightMeasurement);
            }
        });

        // SET NO SCROLLING BEHAVIOUR WHEN ITEMS ARE LITTLE
        /*CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                weightActivityBinding.recyclerView.getLayoutParams();
        params.setBehavior(new ShouldScrolledBehaviour(layoutManager,entryListAdapter));
        weightActivityBinding.recyclerView.setLayoutParams(params);*/

        //ASSIGN ADAPTER
        if (weightActivityBinding.recyclerView.getAdapter() != null){
            weightActivityBinding.recyclerView.swapAdapter(entryListAdapter,true);
        }
        weightActivityBinding.recyclerView.setAdapter(entryListAdapter);
    }

    /**
     * Called when a weight entry trigger is fired either from a fab button or other triggers
     * */
    public void onAddWeight(View view){
        presenter.addWeight();
    }

    /**
     * On returning results from Child activity.
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == currentRequestCode) && (resultCode == 1)){
            //Refresh the graphs and entries
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
