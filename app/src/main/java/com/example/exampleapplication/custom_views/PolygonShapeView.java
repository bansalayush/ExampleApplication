package com.example.exampleapplication.custom_views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.renderscript.Sampler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import java.nio.file.PathMatcher;

/**
 * Created by Ayush on 2019-09-10.
 */
public class PolygonShapeView extends View {

    private static final String TAG = "PolygonShapeView";
    Context context;

    Paint mPaint;
    Path mPath;

    int size;

    int cX = 100;
    int cY = 100;

    public PolygonShapeView(Context context) {
        super(context);
        _init(context);
    }

    public PolygonShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init(context);
    }

    public PolygonShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init(context);
    }

    public PolygonShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        _init(context);
    }

    private void _init(Context c) {
        context = c;
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5.0f);
//        setCornerPathEffect();
    }

    private void setCornerPathEffect() {
        CornerPathEffect cornerPathEffect = new CornerPathEffect(5.0f);
        mPaint.setPathEffect(cornerPathEffect);
    }

    private void setDashPathEffect() {

//        intervals[2] = 20;
//        intervals[3] = 80;

    }

    public void animatePath() {
        PathMeasure measure = new PathMeasure(mPath, false);

        float length = measure.getLength();
        final float[] intervals = new float[2];
        intervals[0] = length;
        intervals[1] = length;
        ValueAnimator phaseAnimation = ValueAnimator.ofFloat(0f, 0.1f, 1f);
        phaseAnimation.setInterpolator(new DecelerateInterpolator());
        phaseAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
                final DashPathEffect dashPathEffect = new DashPathEffect(intervals, (float) animation.getAnimatedValue());
                mPaint.setPathEffect(dashPathEffect);
                invalidate();
            }
        });
        phaseAnimation.setDuration(2000);
        phaseAnimation.setRepeatCount(ValueAnimator.INFINITE);
        phaseAnimation.setRepeatMode(ValueAnimator.RESTART);
        phaseAnimation.start();


    }

    private Path createPath(int sides, float radius) {
        Path polyPath = new Path();

        if (sides < 3) return polyPath;
        radius = size / 4;

        double angle = 2.0 * Math.PI / sides;
        polyPath.moveTo(
                cX + (float) (radius * Math.cos(0.0)),
                cY + (float) (radius * Math.sin(0.0)));
        for (int i = 1; i < sides; i++) {
            polyPath.lineTo(
                    cX + (float) (radius * Math.cos(angle * i)),
                    cY + (float) (radius * Math.sin(angle * i)));
        }
        polyPath.close();
        return polyPath;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.addPath(createPath(5, 45.0f));

        canvas.drawPath(mPath, mPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //to resize the layout

        //get min of layout's height and width
        size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cX = size / 2;
        cY = size / 2;
        setMeasuredDimension(size, size);

    }
}
