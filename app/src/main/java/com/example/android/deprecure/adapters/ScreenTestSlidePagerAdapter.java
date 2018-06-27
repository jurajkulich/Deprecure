package com.example.android.deprecure.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.deprecure.ScreenTestSlideFragment;

import java.util.ArrayList;

/**
 * Created by Juraj on 6/27/2018.
 */

public class ScreenTestSlidePagerAdapter extends FragmentStatePagerAdapter {

    private String mQuestions[];

    public ScreenTestSlidePagerAdapter(FragmentManager fragmentManager, String questions[]) {
        super(fragmentManager);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        return ScreenTestSlideFragment.getInstance(mQuestions[position]);
    }

    @Override
    public int getCount() {
        return mQuestions.length;
    }
}
