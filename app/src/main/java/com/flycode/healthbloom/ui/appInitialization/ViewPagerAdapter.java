package com.flycode.healthbloom.ui.appInitialization;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.flycode.healthbloom.ui.appInitialization.DetailsOverview.DetailsOverviewFragment;
import com.flycode.healthbloom.ui.appInitialization.HeightDetails.HeightDetailsFragment;
import com.flycode.healthbloom.ui.appInitialization.PersonalDetails.PersonalDetailsFragment;
import com.flycode.healthbloom.ui.appInitialization.WeightDetails.WeightDetailsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return PersonalDetailsFragment.newInstance();
            case 1:
                return WeightDetailsFragment.newInstance();
            case 2:
                return HeightDetailsFragment.newInstance();
            case 3:
                return DetailsOverviewFragment.newInstance();
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
        return 4;
    }
}
