package com.flycode.healthbloom.ui.appInitialization;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.ui.base.BaseView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppInitActivity
        extends BaseView
        implements AppInitContract.AppInitView{

    @Inject
    ViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.btn_add_later)
    Button btn_add_later;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.btn_prev)
    Button btn_prev;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_initialization);
        ButterKnife.bind(this);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

                if (currentPage == 0){
                    //if the fast page

                    btn_prev.setVisibility(View.INVISIBLE);
                    btn_add_later.setVisibility(View.GONE);
                }else if(currentPage == (viewPagerAdapter.getCount() - 1)){
                    //if the last page

                    btn_next.setVisibility(View.INVISIBLE);
                    btn_add_later.setText(R.string.finish);
                }else {
                    //if any other page

                    btn_prev.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);
                    btn_add_later.setVisibility(View.VISIBLE);
                    btn_add_later.setText(R.string.btn_add_later);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }


    public void onNext(View view){
        viewPager.setCurrentItem(currentPage + 1);
    }

    public void onPrev(View view){
        viewPager.setCurrentItem(currentPage - 1);
    }
}
