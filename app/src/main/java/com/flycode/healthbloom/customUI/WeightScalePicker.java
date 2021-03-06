package com.flycode.healthbloom.customUI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.utils.MathUtils;

import lombok.Getter;
import lombok.Setter;

public class WeightScalePicker extends FrameLayout{
    @Getter
    private int maximumAcceptedSize;
    @Getter
    private String typeOfUnits = "kg(s)"; //TODO: get default string from weight_unit array
    @Getter
    private float mWeight;
    @Getter @Setter
    private String backgroundColor = "#00ffffff";
    @Getter @Setter
    private String pointerBackgroundColor = "#ffffff";

    @Setter
    private OnWeightChangedListener onWeightChangedListener;

    private Context context;
    private ScaleAdapter scaleAdapter;
    private RecyclerView mRecyclerView;
    private View pointer;
    private WeightScaleSpacer weightScaleSpacer = new WeightScaleSpacer();
    private final int pointerHeight = (int) (getResources().getDisplayMetrics().density * 150);
    private final int pointerWidth = (int) (getResources().getDisplayMetrics().density * 3);
    private final int pointerBottomMargin = (int) (getResources().getDisplayMetrics().density * 10);
    float distance = 0;
    int oneUnitWidth;

    public WeightScalePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //GET ATTRIBUTES
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ScalePicker,
                0, 0);
        try {
            maximumAcceptedSize = a.getInt(R.styleable.ScalePicker_maximumAcceptedSize, 200);
            typeOfUnits = a.getString(R.styleable.ScalePicker_typeOfUnits) != null
                    ? a.getString(R.styleable.ScalePicker_typeOfUnits):  typeOfUnits;
            mWeight = a.getFloat(R.styleable.ScalePicker_mWeight,0f);
            backgroundColor = a.getString(R.styleable.ScalePicker_bgColor) != null
                    ? a.getString(R.styleable.ScalePicker_bgColor):  backgroundColor;
            pointerBackgroundColor = a.getString(R.styleable.ScalePicker_pointerBgColor) != null
                    ? a.getString(R.styleable.ScalePicker_pointerBgColor):  pointerBackgroundColor;
        } finally {
            a.recycle();
        }

        // Create our internal recycler view and add it to the parent frame layout
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        //SCALE ADAPTER
        scaleAdapter = new ScaleAdapter(context,ScaleAdapter.HORIZONTAL_ORIENTATION);
        scaleAdapter.setBackgroundColor(Color.parseColor(backgroundColor));
        scaleAdapter.setMaximumSize(maximumAcceptedSize);
        scaleAdapter.setTypeOfUnit(typeOfUnits);
        mRecyclerView.setAdapter(scaleAdapter);
        mRecyclerView.addItemDecoration(weightScaleSpacer);

        mRecyclerView.setHorizontalScrollBarEnabled(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            public void onScrollStateChanged(RecyclerView recyclerView, int newState){}

            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);

                distance += dx;

                if (oneUnitWidth != 0)
                    mWeight = MathUtils.round(distance / oneUnitWidth,1);
                if(onWeightChangedListener != null)
                    onWeightChangedListener.OnWeightChanged(mWeight,typeOfUnits);
            }
        });

        //CREATE POINTER
        pointer = new View(context);
        pointer.setBackgroundColor(Color.parseColor(pointerBackgroundColor));
        pointer.setLayoutParams(new FrameLayout.LayoutParams(
                pointerWidth,
                pointerHeight)
        );

        addView(mRecyclerView);
        addView(pointer);
    }

    /**
     * Add spaces to the scale.
     * */
    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            // Layout the spacers now that we are measured
            final int width = getWidth();
            int itemOffset = width / 2;

            weightScaleSpacer.setOffset(itemOffset);

            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) pointer.getLayoutParams();
            layoutParams.setMargins(itemOffset, 0,0,0);
            pointer.setLayoutParams(layoutParams);

            //GET ONE UNIT LENGTH
            View view = mRecyclerView.getChildAt(1);
            if(view != null)
                oneUnitWidth = view.getWidth();
        }
    }
    /**
     * Cause the recycler view to redraw with the new size
     *
     * */
    public void setMaximumAcceptedSize(int maximumAcceptedSize) {
        this.maximumAcceptedSize = maximumAcceptedSize;
        scaleAdapter.setMaximumSize(maximumAcceptedSize);
        scaleAdapter.notifyDataSetChanged();
    }

    /**
     * Cause the recycler view to redraw with the new units
     *
     * */
    public void setTypeOfUnits(String typeOfUnits) {
        this.typeOfUnits = typeOfUnits;
        scaleAdapter.setTypeOfUnit(typeOfUnits);
        scaleAdapter.notifyDataSetChanged();
    }

    public void setMWeight(float mWeight) {
        this.mWeight = mWeight;
        mRecyclerView.scrollTo((int) (mWeight * oneUnitWidth),0);
    }

    /**
     * Interface for listening to changes in weight.
     * */
    public interface OnWeightChangedListener{
        void OnWeightChanged(float mWeight,String typeOfUnits);
    }
}
