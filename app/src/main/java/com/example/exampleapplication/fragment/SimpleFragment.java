package com.example.exampleapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.exampleapplication.R;
import com.example.exampleapplication.interfaces.OnFragmentInteractionListener;

/**
 * Created by Ayush on 14/06/20.
 */
public class SimpleFragment extends Fragment {

    private static final int YES = 0;
    private static final int NO = 1;
    public static final int NONE = 2;

    private RatingBar ratingBar;
    private OnFragmentInteractionListener mListener;


    public SimpleFragment() {
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

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple,
                container, false);
        int radioChoice = NONE;
        if (savedInstanceState != null) {
            radioChoice = savedInstanceState.getInt("BUNDLE_RADIO_CHOICE");
        } else {
            Bundle b = getArguments();
            if (b != null)
                radioChoice = b.getInt("BUNDLE_RADIO_CHOICE");
        }

        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                TextView textView =
                        rootView.findViewById(R.id.fragment_header);
                switch (index) {
                    case YES: // User chose "Yes."
                        textView.setText("Yes");
                        mListener.onRadioButtonChoice(YES);
                        break;
                    case NO: // User chose "No."
                        textView.setText("No");
                        mListener.onRadioButtonChoice(NO);
                        break;
                    default: // No choice made.
                        // Do nothing.
                        mListener.onRadioButtonChoice(NONE);
                        break;
                }
            }
        });

        if (radioChoice != NONE) {
            radioGroup.check
                    (radioGroup.getChildAt(radioChoice).getId());
        }

        ((RatingBar) rootView.findViewById(R.id.ratingBar))
                .setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        Toast.makeText(getActivity(), "Rating" + rating, Toast.LENGTH_SHORT).show();
                    }
                });
        return rootView;
    }

    public static SimpleFragment newInstance(int radioChoice) {
        Bundle b = new Bundle();
        b.putInt("BUNDLE_RADIO_CHOICE", radioChoice);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(b);
        return fragment;
    }
}
