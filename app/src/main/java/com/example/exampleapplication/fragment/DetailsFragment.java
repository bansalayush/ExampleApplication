package com.example.exampleapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.exampleapplication.R;

/**
 * Created by Ayush on 15/06/20.
 */
public class DetailsFragment extends Fragment {
    public DetailsFragment() {
    }

    public static DetailsFragment newInstance() {
        DetailsFragment detailsFragment = new DetailsFragment();
        return detailsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_layout, container, false);
    }
}
