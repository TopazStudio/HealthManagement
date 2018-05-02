package com.flycode.healthbloom.ui.weight.WeightOverview.WeightGraph;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.ui.base.BaseFragment;
import com.flycode.healthbloom.utils.XAxisDateValueFormatter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import javax.inject.Inject;


public class WeightGraphFragment
        extends BaseFragment
        implements WeightGraphContract.WeightGraphView{

    @Inject
    WeightGraphContract.WeightGraphPresenter<WeightGraphContract.WeightGraphView> presenter;

    private LineChart chart;
    private Spinner period_filter;

    public WeightGraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight_graph, container, false);
        presenter.onAttach(this);
        return view;
    }

    @Override
    protected void setUp(View view) {
        chart = (LineChart) getBaseActivity().findViewById(R.id.weight_graph);
        period_filter = (Spinner) getBaseActivity().findViewById(R.id.period_filter);
        setUpChart();
        setUpPeriod_filter();

        //FETCH DATA
        presenter.getPerDayLineDataSet();
    }

    /**
     * Set up the Line Chart
     * */
    private void setUpChart(){
        //CONFIGURE
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.animateX(3000, Easing.EasingOption.EaseInOutBack);
        chart.getLegend().setTextColor(Color.WHITE);


        //SET NO DATA TEXT
        chart.setNoDataText("Add you weight to start view your statistics today!!!");
        chart.setNoDataTextColor(Color.WHITE);

        //SET UP THE AXES
        setupYAxis();
        setupXAxis();
    }

    /**
     * Set up the period spinner. This spinner will be call the presenter to
     * retrieve filtered data according to the selection.
     * */
    private void setUpPeriod_filter(){
        //INIT PERIOD FILTER
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseActivity(),
                R.array.weight_data_filter,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period_filter.setAdapter(adapter);

        period_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        //Per Day
                        presenter.getPerDayLineDataSet();
                    case 1:
//                        presenter.getPerMonthLineDataSet();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setLineDataSet(LineDataSet weightDataSet) {
        //SET DATA-SET COLORS
        weightDataSet.setCircleColorHole(Color.WHITE);
        weightDataSet.setCircleColor(Color.WHITE);
        weightDataSet.setHighlightEnabled(true);
        weightDataSet.setValueTextColor(Color.WHITE);
        weightDataSet.setColor(Color.WHITE);
        weightDataSet.setDrawFilled(true);
        weightDataSet.setFillColor(Color.argb(154,255,255,255));

        //SET CHART DATA
        if(chart.isEmpty()){
            chart.setData(new LineData(weightDataSet));
            chart.invalidate();
        }else {
            chart.clear();
            chart.setData(new LineData(weightDataSet));
        }
    }

    /**
     * Setup the Y Axis including all BMI limits
     * */
    private void setupYAxis(){
        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setAxisMaximum(50f);
//        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawLimitLinesBehindData(true);
    }

    /**
     * Setup the X Axis including adding a custom date value
     * formatter
     * */
    private void setupXAxis(){
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new XAxisDateValueFormatter());
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

}
