package com.flycode.healthbloom.ui.weight.WeightOverview.BMIGraph;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.Objects;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class BMIGraphFragment
        extends BaseFragment
        implements BMIGraphContract.BMIGraphView{

    @Inject
    BMIGraphContract.BMIGraphPresenter<BMIGraphContract.BMIGraphView> presenter;

    private LineChart chart;
    private Spinner period_filter;
    private float limitLabelTextSize = 8f;
    private int limitLabelTextColor = Color.WHITE;

    public BMIGraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmigraph, container, false);
        presenter.onAttach(this);
        return view;
    }

    @Override
    protected void setUp(View view) {
        chart = (LineChart) Objects.requireNonNull(getActivity()).findViewById(R.id.bmi_graph);
        period_filter = (Spinner) getBaseActivity().findViewById(R.id.period_filter);
        setUpChart();
        setUpPeriod_filter();

        //FETCH DATA
        presenter.getLineDataSet();
    }

    /**
     * Set up the Line Chart
     * */
    private void setUpChart(){
        //CONFIGURATION
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
                        presenter.getLineDataSet();
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
    public void setLineDataSet(LineDataSet BMIDataSet) {
        //SET DATA-SET COLORS
        BMIDataSet.setCircleColorHole(Color.WHITE);
        BMIDataSet.setCircleColor(Color.WHITE);
        BMIDataSet.setHighlightEnabled(true);
        BMIDataSet.setValueTextColor(Color.WHITE);
        BMIDataSet.setColor(Color.WHITE);
        BMIDataSet.setDrawFilled(true);
        BMIDataSet.setFillColor(Color.argb(154,255,255,255));

        //SET CHART DATA
        chart.setData(new LineData(BMIDataSet));
    }

    /**
     * Setup the Y Axis including all BMI limits
     * */
    private void setupYAxis(){
        //UNDERWEIGHT LIMIT
        LimitLine underweight = new LimitLine(18.5f,"Underweight");
        underweight.setLineWidth(1f);
        underweight.setTextColor(limitLabelTextColor);
        underweight.setTextSize(limitLabelTextSize);
        underweight.setLineColor(Color.RED);
        underweight.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);

        //NORMAL LIMIT
        LimitLine normal = new LimitLine(25.0f,"Normal");
        normal.setLineWidth(1f);
        normal.setTextColor(limitLabelTextColor);
        normal.setTextSize(limitLabelTextSize);
        normal.setLineColor(Color.GREEN);
        normal.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);

        //OVERWEIGHT LIMIT
        LimitLine overweight = new LimitLine(30.0f,"Overweight");
        overweight.setLineWidth(1f);
        overweight.setTextColor(limitLabelTextColor);
        overweight.setTextSize(limitLabelTextSize);
        overweight.setLineColor(Color.YELLOW);
        overweight.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);

        //OBESE LIMIT
        LimitLine obese = new LimitLine(40.0f,"Obese");
        obese.setLineWidth(1f);
        obese.setTextColor(limitLabelTextColor);
        obese.setTextSize(limitLabelTextSize);
        obese.setLineColor(Color.RED);
        obese.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);

        chart.getAxisRight().setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(obese);
        leftAxis.addLimitLine(normal);
        leftAxis.addLimitLine(overweight);
        leftAxis.addLimitLine(underweight);
        leftAxis.setAxisMaximum(50f);
        leftAxis.setAxisMinimum(0f);
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
    public void refresh() {
        chart.invalidate();
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
