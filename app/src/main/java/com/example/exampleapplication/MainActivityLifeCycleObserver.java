package com.example.exampleapplication;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by Ayush on 24/06/20.
 */
public class MainActivityLifeCycleObserver implements LifecycleObserver {
    private static final String TAG = "MainActivityLifeCycleOb";
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
    }

}
