package com.example.exampleapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

import static com.example.exampleapplication.constants.Statics.DEFAULT_SAMPLE_RATE;

/**
 * Created by Ayush on 2019-11-27.
 */
public class RecordingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAndRequestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 1002);

//        File mp4Directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
//        File[] mp4Files = mp4Directory.listFiles();
    }

    private boolean checkAndRequestPermission(Context context, String permission, int permissionRequestCode) {
        if (ContextCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            //ask for permission
            ActivityCompat.requestPermissions(this, new String[]{permission}, permissionRequestCode);
        }
        //permission granted
        return true;
    }
}
