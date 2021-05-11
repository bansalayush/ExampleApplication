package com.example.exampleapplication;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class CustomSageeredLayoutManager extends StaggeredGridLayoutManager {


    public CustomSageeredLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomSageeredLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }


}
