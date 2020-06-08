package com.example.exampleapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;

public class SearchActivity extends AppCompatActivity {

    public static String EXTRA_PARAM_ID;
    public static String VIEW_NAME_SEARCH_ICON = "VIEW_NAME_SEARCH_ICON";

    AppBarLayout mSearchBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchBarLayout = findViewById(R.id.appbar_layout);

        ViewCompat.setTransitionName(mSearchBarLayout, VIEW_NAME_SEARCH_ICON);
    }
}
