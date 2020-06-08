package com.example.exampleapplication.custom_views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by Ayush on 2019-10-20.
 */
public class SolarSystemView extends View {

    private static final String TAG = "SolarSystemView";
    Context context;
    Canvas canvas;

    Paint mPaint;
    Path mOrbit;
    Path mPlanet;

    PathDashPathEffect effect;
    public ValueAnimator phaseAnimator;

    float cX, cY;

    int size;

    public SolarSystemView(Context context) {
        super(context);
        init(context);
    }

    public SolarSystemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SolarSystemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SolarSystemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        mOrbit.reset();
        drawOrbitAndPlanet(canvas);
    }

    private void init(Context context) {
        this.context = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);

        mOrbit = new Path();
        mPlanet = new Path();
        mPlanet.addCircle(10, 10, 10, Path.Direction.CCW);
        //animate phase from 0 to 2PiR and we will have our animation

        effect = new PathDashPathEffect(mPlanet, (float) (2 * Math.PI * 394), 0, PathDashPathEffect.Style.TRANSLATE);
        mPaint.setPathEffect(effect);

    }

    private void drawOrbitAndPlanet(Canvas canvas) {

        mOrbit.addCircle(cX, cY, 200, Path.Direction.CW);
        canvas.drawPath(mOrbit, mPaint);
    }

    public void startAnimationHere() {
        phaseAnimator = ValueAnimator.ofInt(0, (int) (2 * Math.PI * 394));
        phaseAnimator.setDuration(5000);
        phaseAnimator.setRepeatMode(ValueAnimator.RESTART);
        phaseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        phaseAnimator.setInterpolator(new LinearInterpolator());
        phaseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int phase = (int) animation.getAnimatedValue();
                Log.d(TAG, "onAnimationUpdate: phase" + phase);
                effect = new PathDashPathEffect(mPlanet, phase, phase, PathDashPathEffect.Style.TRANSLATE);
                mPaint.setPathEffect(effect);
                invalidate();
            }
        });
        phaseAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //to resize the layout

        //get min of layout's height and width
        size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cX = size / 2;
        cY = size / 2;
        Log.d(TAG, cX + " ");
        setMeasuredDimension(size, size);

    }
}
