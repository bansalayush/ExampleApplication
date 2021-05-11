package com.example.exampleapplication;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class ExampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
