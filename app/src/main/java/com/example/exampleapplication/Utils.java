package com.example.exampleapplication;

import android.app.Activity;
import android.content.Intent;

import com.example.exampleapplication.utils.SharedPrefsUtil;

/**
 * Created by Ayush on 2019-12-01.
 */
public class Utils {

    public static void startRxMainActivity(Activity mainActivity) {
        Intent i = new Intent(mainActivity, MainActivityForRx.class);
        mainActivity.startActivity(i);
    }
}
