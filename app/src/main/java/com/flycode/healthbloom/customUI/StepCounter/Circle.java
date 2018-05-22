package com.flycode.healthbloom.customUI.StepCounter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.utils.DrawUtils;

import lombok.Setter;

public class Circle extends View {

    @Setter
    int radius;
    @Setter
    int strokeWidth;
    @Setter
    String circleColor = "#ffffff";

    Paint circlePaint;

    private Context context;

    public Circle(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //GET ATTRIBUTES
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Circle,
                0, 0);
        try {
            circleColor = a.getString(R.styleable.Circle_circleColor) != null
                    ? a.getString(R.styleable.Circle_circleColor):  circleColor;
            strokeWidth = a.getInt(R.styleable.Circle_strokeWidth,3);
            radius = a.getInt(R.styleable.Circle_radius,50);

        } finally {
            a.recycle();
        }

        init();
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init(){
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.parseColor(circleColor));
        circlePaint.setStrokeWidth(DrawUtils.convertDpToPixel(strokeWidth,context));

        radius = (int) DrawUtils.convertDpToPixel(radius,context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();
        canvas.drawCircle(x/2,y/2, radius,circlePaint);
    }
}
