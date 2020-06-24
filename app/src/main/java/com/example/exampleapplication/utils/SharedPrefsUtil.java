package com.example.exampleapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;

/**
 * Created by Ayush on 24/06/20.
 */
public class SharedPrefsUtil {

    private static SharedPreferences mPrefs;

    public static void loadPrefs(Context context) {
        mPrefs = context.getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        return mPrefs.edit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return mPrefs.getBoolean(key, defaultValue);
    }

    @UiThread
    public static void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    @WorkerThread
    public static void putBooleanSync(String key, boolean value) {
        getEditor().putBoolean(key, value).commit();
    }
}
