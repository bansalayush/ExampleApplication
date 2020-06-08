package com.example.exampleapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.exampleapplication.fragment.MotionLayoutFragment;

import static com.example.exampleapplication.constants.Statics.EXTRA_STRING;

/**
 * Created by Ayush on 2019-08-28.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        MotionLayoutFragment motionLayoutFragment = new MotionLayoutFragment();

        Bundle b = new Bundle();
        switch (position) {
            case 0:
                b.putString(EXTRA_STRING, "Kotlin");
                break;

            case 1:
                b.putString(EXTRA_STRING, "java");
                break;

            case 2:
                b.putString(EXTRA_STRING, "Swift");
                break;

            case 3:
                b.putString(EXTRA_STRING, "Javascript");
                break;
        }

        motionLayoutFragment.setArguments(b);

        return motionLayoutFragment;
    }
}
