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

public class HeightScalePicker extends FrameLayout {
    @Getter
    private int maximumAcceptedSize;
    @Getter
    private String typeOfUnits = "kg(s)"; //TODO: get default string from height_unit array
    @Getter
    private float mHeight;
    @Getter @Setter
    private String backgroundColor = "#00ffffff";
    @Getter @Setter
    private String pointerBackgroundColor = "#ffffff";

    @Setter
    private HeightScalePicker.OnHeightChangedListener onHeightChangedListener;

    private Context context;
    private ScaleAdapter scaleAdapter;
    private RecyclerView mRecyclerView;
    private View pointer;
    private HeightScaleSpacer heightScaleSpacer = new HeightScaleSpacer();
    private final int pointerHeight = (int) (getResources().getDisplayMetrics().density * 3);
    private final int pointerWidth = (int) (getResources().getDisplayMetrics().density * 150);
    float distance = 0;
    int oneUnitHeight;

    public HeightScalePicker(Context context, AttributeSet attrs) {
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
            mHeight = a.getFloat(R.styleable.ScalePicker_mWeight,0f);
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
                new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        //SCALE ADAPTER
        scaleAdapter = new ScaleAdapter(context,ScaleAdapter.VERTICAL_ORIENTATION);
        scaleAdapter.setBackgroundColor(Color.parseColor(backgroundColor));
        scaleAdapter.setMaximumSize(maximumAcceptedSize);
        scaleAdapter.setTypeOfUnit(typeOfUnits);
        mRecyclerView.setAdapter(scaleAdapter);
        mRecyclerView.addItemDecoration(heightScaleSpacer);

        mRecyclerView.setHorizontalScrollBarEnabled(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            public void onScrollStateChanged(RecyclerView recyclerView, int newState){}

            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);

                distance += dy;

                if (oneUnitHeight != 0)
                    mHeight = MathUtils.round(distance / oneUnitHeight,1);
                if(onHeightChangedListener != null)
                    onHeightChangedListener.OnHeightChanged(mHeight,typeOfUnits);
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
            final int height = getHeight();
            int itemOffset = height / 2;

            //SET UP SPACERS
            heightScaleSpacer.setOffset(itemOffset);

            //SET UP POINTER
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) pointer.getLayoutParams();
            layoutParams.setMargins(0,itemOffset, 0,0);
            pointer.setLayoutParams(layoutParams);

            //GET ONE UNIT LENGTH
            View view = mRecyclerView.getChildAt(1);
            if(view != null)
                oneUnitHeight = view.getHeight();
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

    public void setMHeight(float mHeight) {
        this.mHeight = mHeight;
        mRecyclerView.scrollTo((int) (mHeight * oneUnitHeight),0);
    }

    /**
     * Interface for listening to changes in height.
     * */
    public interface OnHeightChangedListener{
        void OnHeightChanged(float mHeight,String typeOfUnits);
    }
}
