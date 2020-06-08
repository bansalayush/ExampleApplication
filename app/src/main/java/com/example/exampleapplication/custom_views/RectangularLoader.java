package com.example.exampleapplication.custom_views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Ayush on 2019-09-21.
 */
public class RectangularLoader extends View {
    private static final String TAG = "RectangularLoader";

    Context context;

    Paint outerBoundsPaint, innerBoundsPaint;
    RectF outerRect, innerRect;
    Canvas canvas;

    //dimen for outer rect
    int outerLeft = 200;
    int outerTop = 100;
    int outerRight = 700;
    int outerBottom = 200;

    // dimen for inner rect
    int innerLeft = outerLeft + 10,
            innerTop = outerTop + 10,
            innerBottom = outerBottom - 10;
    int innerRight = innerLeft + (innerBottom - innerTop);


    int finalPosition = outerRight - 10;
    int progress;

    private final int ANIMATION_DURATION = 4000;


    public RectangularLoader(Context context) {
        super(context);
        init(context);
    }

    public RectangularLoader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RectangularLoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RectangularLoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        outerBoundsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerBoundsPaint.setColor(Color.BLUE);
        outerBoundsPaint.setStyle(Paint.Style.STROKE);
        outerBoundsPaint.setStrokeWidth(5f);

        innerBoundsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerBoundsPaint.setColor(Color.BLUE);
        innerBoundsPaint.setStyle(Paint.Style.FILL);
        innerBoundsPaint.setStrokeWidth(5f);

        outerRect = new RectF(outerLeft, outerTop, outerRight, outerBottom);
        innerRect = new RectF(innerLeft, innerTop, innerRight, innerBottom);
    }

    public void expandCircleAnim() {
        ValueAnimator widthAnimator = ValueAnimator.ofInt(innerRight, finalPosition);
        widthAnimator.setDuration(ANIMATION_DURATION);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (int) animation.getAnimatedValue();
                Log.d(TAG, "onAnimationUpdate: " + progress);
                innerRect = new RectF(innerLeft, innerTop, progress, innerBottom);
                invalidate();
            }
        });

        widthAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.d(TAG, "onAnimationEnd: " + "animation has ended ");
                expandInnerRoundRect();
            }
        });
        widthAnimator.start();
    }

    public void expandInnerRoundRect() {
        ValueAnimator leftAnimator = ValueAnimator.ofInt(1, 100);
        leftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                innerRect = new RectF(getLeftProgressValue((int) animation.getAnimatedValue()),
                        getTopProgressValue((int) animation.getAnimatedValue()),
                        getRightProgressValue((int) animation.getAnimatedValue()),
                        getBottomProgressValue((int) animation.getAnimatedValue()));
                invalidate();
            }
        });

        leftAnimator.setDuration(2000);
        leftAnimator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
        this.canvas = canvas;

        canvas.drawRoundRect(outerRect, 20, 20, outerBoundsPaint);
        canvas.drawRoundRect(innerRect, 20,20, innerBoundsPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //to resize the layout

        //get min of layout's height and width
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);

    }

    private int getLeftProgressValue(int outerProgress) {
        float valueProgress = (float) ((-10 * outerProgress) + 20800) / 99;
        return Math.round(valueProgress);
    }

    private int getTopProgressValue(int outerProgress) {
        float valueProgress = (float) ((-10 * outerProgress) + 10900) / 99;
        return Math.round(valueProgress);
    }

    private int getRightProgressValue(int outerProgress) {
        float valueProgress = (float) ((10 * outerProgress) + 68300) / 99;
        return Math.round(valueProgress);
    }

    private int getBottomProgressValue(int outerProgress) {
        float valueProgress = (float) ((10 * outerProgress) + 18800) / 99;
        return Math.round(valueProgress);
    }
}
