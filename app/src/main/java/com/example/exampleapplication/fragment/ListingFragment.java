package com.example.exampleapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.exampleapplication.R;
import com.example.exampleapplication.interfaces.OnFragmentInteractionListener;

/**
 * Created by Ayush on 15/06/20.
 */
//This is the listing fragment
public class ListingFragment extends Fragment {
    private AppCompatTextView tvClickMe;
    private View rootView;
    private OnFragmentInteractionListener mListener;

    public ListingFragment() {
    }

    public static ListingFragment newInstance() {
        return new ListingFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException("OnAttach Listener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_layout, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvClickMe = rootView.findViewById(R.id.tv_clickMe);
        tvClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickMe();
            }
        });
    }
}
