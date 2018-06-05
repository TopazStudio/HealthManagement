package com.flycode.healthbloom.ui.exercise.exerciseView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.flycode.healthbloom.ui.exercise.exerciseView.fragments.HeartRateDetailsFragment;
import com.flycode.healthbloom.ui.exercise.exerciseView.fragments.PaceDetailsFragment;

public class StepsUpperDetailsViewPagerAdapter extends FragmentStatePagerAdapter {
    public StepsUpperDetailsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new HeartRateDetailsFragment();
            case 1:
                return new PaceDetailsFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
