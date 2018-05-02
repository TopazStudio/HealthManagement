package com.flycode.healthbloom.customUI;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.utils.MathUtils;

import lombok.Getter;
import lombok.Setter;

public class WeightScalePicker extends FrameLayout{
    @Getter
    private int maximumAcceptedSize;
    @Getter
    private String typeOfUnits = "Kgs";
    @Getter
    private float weight;
    @Setter
    private OnWeightChangedListener onWeightChangedListener;

    private Context context;
    private RecyclerView mRecyclerView;
    float distance =0;
    int scrollDirection;

    public WeightScalePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //GET ATTRIBUTES
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.WeightScalePicker,
                0, 0);

        try {
            maximumAcceptedSize = a.getInt(R.styleable.WeightScalePicker_maximumAcceptedSize, 200);
            typeOfUnits = a.getString(R.styleable.WeightScalePicker_typeOfUnits);
            weight = a.getFloat(R.styleable.WeightScalePicker_weight,0f);
        } finally {
            a.recycle();
        }

        // Create our internal recycler view and add it to the parent frame layout
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new ScaleAdapter(context, maximumAcceptedSize, typeOfUnits));
        mRecyclerView.setHorizontalScrollBarEnabled(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                /*if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (scrollDirection > 0) {
                        Log.d("TAG", "onScrollStateChanged: " + " STOPPED " + distance);
                        distance = 0;
                    } else {
                        Log.d("TAG", "onScrollStateChanged: " + " STOPPED " + distance);
                        distance = 0;
                    }
                }*/
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);

                scrollDirection = dx;
                distance += dx;

                weight = MathUtils.round(distance / getOneUnitDistance(),1);
                onWeightChangedListener.OnWeightChanged(weight,typeOfUnits);
            }
        });

        addView(mRecyclerView);
    }

    /**
     * Get the distance of one unit in the recycler in order
     * to get distance covered per unit
     * */
    private float getOneUnitDistance(){
        return 140f;
    }

    /**
     * Add width to the spaces
     *
     * *//*
    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            // Layout the spacers now that we are measured
            final int width = getWidth();

            final ViewGroup.LayoutParams leftParams = mLeftSpacer.getLayoutParams();
            leftParams.width = width / 2;
            mLeftSpacer.setLayoutParams(leftParams);

            final ViewGroup.LayoutParams rightParams = mRightSpacer.getLayoutParams();
            rightParams.width = width / 2;
            mRightSpacer.setLayoutParams(rightParams);
        }
    }
*/
    /**
     * Cause the recycler view to redraw with the new size
     *
     * */
    public void setMaximumAcceptedSize(int maximumAcceptedSize) {
        this.maximumAcceptedSize = maximumAcceptedSize;
        mRecyclerView.setAdapter(new ScaleAdapter(context, maximumAcceptedSize, typeOfUnits));
    }

    /**
     * Cause the recycler view to redraw with the new units
     *
     * */
    public void setTypeOfUnits(String typeOfUnits) {
        this.typeOfUnits = typeOfUnits;
        mRecyclerView.setAdapter(new ScaleAdapter(context, maximumAcceptedSize, typeOfUnits));
    }

    //TODO: scroll to the particular weight
    public void setWeight(float weight) {
        this.weight = weight;
//        mRecyclerView.scrollTo((int) weight,0);
    }

    /**
     * Interface for listening to changes in weight.
     * */
    public interface OnWeightChangedListener{
        void OnWeightChanged(float weight,String typeOfUnits);
    }
}
