package com.example.exampleapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.exampleapplication.R;

import static com.example.exampleapplication.constants.Statics.EXTRA_STRING;

/**
 * Created by Ayush on 2019-08-28.
 */
public class MotionLayoutFragment extends Fragment {

    View parentView;

    AppCompatTextView textView;

    Bundle mArgs;
    String text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArgs = mArgs != null ? mArgs : getArguments();
        text = mArgs.getString(EXTRA_STRING);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.motionlayout_content, container, false);
        textView = parentView.findViewById(R.id.tv_sometext);
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView.setText(text);
    }
}
