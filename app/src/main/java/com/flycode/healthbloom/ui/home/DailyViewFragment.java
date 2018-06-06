package com.flycode.healthbloom.ui.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flycode.healthbloom.R;
import com.test.tudou.library.model.CalendarDay;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyViewFragment extends Fragment {

    private CalendarDay calendarDay;

    public DailyViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){

    }

    public static DailyViewFragment newInstance(CalendarDay calendarDay){
        DailyViewFragment dailyViewFragment = new DailyViewFragment();
        dailyViewFragment.calendarDay = calendarDay;
        return dailyViewFragment;
    }
}
