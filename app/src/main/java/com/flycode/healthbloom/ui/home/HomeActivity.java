package com.flycode.healthbloom.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.ui.base.BaseViewWithNav;
import com.flycode.healthbloom.ui.exercise.exerciseEntry.ExerciseEntryActivity;
import com.flycode.healthbloom.ui.weight.weightEntry.WeightEntryActivity;
import com.flycode.healthbloom.ui.weight.weightOverview.WeightOverviewActivity;
import com.test.tudou.library.WeekPager.adapter.WeekViewAdapter;
import com.test.tudou.library.WeekPager.view.WeekDayViewPager;
import com.test.tudou.library.WeekPager.view.WeekRecyclerView;
import com.test.tudou.library.model.CalendarDay;
import com.test.tudou.library.util.DayUtils;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class HomeActivity
        extends BaseViewWithNav
        implements HomeContract.HomeView, WeekDayViewPager.DayScrollListener {

    @Inject
    HomeContract.HomePresenter<HomeContract.HomeView> presenter;

    private int OFFSCREEN_PAGE_LIMIT = 3;
    private DailyViewAdapter dailyPagerAdapter;
    private WeekViewAdapter weekViewAdapter;

    private WeekDayViewPager weekDayViewPager;
    private WeekRecyclerView weekRecyclerView;
    private TextView textDayLabel;
    private LinearLayout stepsFabLayout;
    private LinearLayout weightFabLayout;
    private FloatingActionButton fabAdd;
    /**
     * Initialize data-binding on the activities layout and attach the presenter.
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ADD CONTENT
        View view = getLayoutInflater().inflate(R.layout.activity_home,null);
        baseActivityBinding.contentFrame.addView(view.getRootView());

        //PRESENTER
        presenter.onAttach(this);

        //TOOLBAR
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //INIT
        init();
    }

    private void init() {
        weekDayViewPager = findViewById(R.id.weekDayViewPager);
        weekRecyclerView = findViewById(R.id.weekRecyclerView);
        textDayLabel = findViewById(R.id.text_day_label);
        weightFabLayout = findViewById(R.id.weight_fab_layout);
        stepsFabLayout = findViewById(R.id.steps_fab_layout);
        fabAdd = findViewById(R.id.fab_add);

        setUpPager();
        setUpData();

    }

    private void setUpPager() {
        //The view pager
        dailyPagerAdapter = new DailyViewAdapter(getSupportFragmentManager());
        weekDayViewPager.setOffscreenPageLimit( OFFSCREEN_PAGE_LIMIT);
        weekDayViewPager.setAdapter(dailyPagerAdapter);
        weekDayViewPager.setWeekRecyclerView(weekRecyclerView);
        weekDayViewPager.setDayScrollListener(this);
        weekViewAdapter = new WeekViewAdapter(getBaseContext(),weekDayViewPager);
        weekViewAdapter.setTextNormalColor(getBaseContext().getResources().getColor(R.color.white));
        weekViewAdapter.setTextSelectColor(getBaseContext().getResources().getColor(R.color.white));

        //The recycler view
        weekRecyclerView.setAdapter(weekViewAdapter);
    }

    private void setUpData() {
        ArrayList<CalendarDay> reachAbleDays = new ArrayList<>();
        //TODO: yearly range
        reachAbleDays.add(new CalendarDay(2018, 1, 1));
        reachAbleDays.add(new CalendarDay(2018, 12, 31));
        weekViewAdapter.setData(reachAbleDays.get(0), reachAbleDays.get(reachAbleDays.size() - 1), null);
        dailyPagerAdapter.setData(reachAbleDays.get(0), reachAbleDays.get(reachAbleDays.size() - 1));
    }

    @Override
    public void onDayPageScrolled(int position , float postionOffset,int positionOffesetPixels) {
        textDayLabel.setText(DayUtils.formatEnglishTime(dailyPagerAdapter.getDatas().get(position).getTime()));
    }

    @Override
    public void onDayPageSelected(int position) {

    }

    @Override
    public void onDayPageScrollStateChanged(int state) {

    }

    public void onFabAdd(View view){
        if(weightFabLayout.getVisibility() == View.VISIBLE
                && stepsFabLayout.getVisibility() == View.VISIBLE){
            //WHEN VISIBLE
            closeFabButton();
        }else{
            //WHEN HIDDEN
            openFabButton();
        }
    }
    private void closeFabButton(){
        fabAdd.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.anim_fab_button_onhide));
        stepsFabLayout.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.hide_fab_hidden_buttons));
        weightFabLayout.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.hide_fab_hidden_buttons));
        weightFabLayout.setVisibility(View.GONE);
        stepsFabLayout.setVisibility(View.GONE);
    }

    private void openFabButton(){
        weightFabLayout.setVisibility(View.VISIBLE);
        stepsFabLayout.setVisibility(View.VISIBLE);
        fabAdd.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.anim_fab_button_onshow));
        stepsFabLayout.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.show_fab_hidden_buttons));
        weightFabLayout.startAnimation(
                AnimationUtils.loadAnimation(this,R.anim.show_fab_hidden_buttons));
    }

    public void onAddSteps(View view){
        closeFabButton();
        finishAndGoTo(ExerciseEntryActivity.class);
    }

    public void onCalorieCardClick(View view) {
        Toast.makeText(this,"Coming Soon",Toast.LENGTH_LONG).show();
    }

    public void onWeightCardClick(View view){
        closeFabButton();
        finishAndGoTo(WeightOverviewActivity.class);
    }

    public void onWaterCardClick(View view){
        Toast.makeText(this,"Coming Soon",Toast.LENGTH_LONG).show();
    }

    public void onAddWeight(View view){
        closeFabButton();
        finishAndGoTo(WeightEntryActivity.class);
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
