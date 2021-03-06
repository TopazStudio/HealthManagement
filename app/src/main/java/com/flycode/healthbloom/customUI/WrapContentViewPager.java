package com.flycode.healthbloom.customUI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.flycode.healthbloom.R;

import java.util.Objects;

public class WrapContentViewPager extends ViewPager {

    private boolean defaultToWindow;

    public WrapContentViewPager(Context context) {
        super(context);
        init();
    }

    public WrapContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.WrapContentViewPager,
                0, 0);
        try {
            defaultToWindow = a.getBoolean(R.styleable.WrapContentViewPager_defaultToWindow, true);

        } finally {
            a.recycle();
        }
        init();
    }

    private Point windowSize;

    private void init(){
        //GET ATTRIBUTES
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = Objects.requireNonNull(windowManager).getDefaultDisplay();
        windowSize = new Point();
        display.getSize(windowSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(getCurrentItem());
        if (child != null) {
            //Tell child it can be as tall as it wants
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            //Get the height that the child measured
            int h = child.getMeasuredHeight();

            //TODO: If the child is height than the available screen height add the screen height as the measure spec
            //Tell super that it needs to be exactly what the child wants
//            int windowHeight = getContext()
            if (h < windowSize.y && defaultToWindow){
                //Smaller than window
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(windowSize.y, MeasureSpec.EXACTLY);
            }else heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



}