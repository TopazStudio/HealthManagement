package com.flycode.healthbloom.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.test.tudou.library.WeekPager.adapter.WeekPagerAdapter;

public class DailyViewAdapter extends WeekPagerAdapter{

    public DailyViewAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    protected Fragment createFragmentPager(int i) {
        return DailyViewFragment.newInstance(mDays.get(i));
    }
}
