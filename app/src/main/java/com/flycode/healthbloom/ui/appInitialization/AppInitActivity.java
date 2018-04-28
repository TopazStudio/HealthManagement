package com.flycode.healthbloom.ui.appInitialization;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.flycode.healthbloom.R;
import com.flycode.healthbloom.databinding.AppInitBinding;
import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.ui.base.BaseView;

import javax.inject.Inject;

public class AppInitActivity
        extends BaseView
        implements AppInitContract.AppInitView{

    @Inject
    ViewPagerAdapter viewPagerAdapter;
    @Inject
    AppInitContract.AppInitPresenter<AppInitContract.AppInitView> presenter;
    @Inject
    User user;

    private int currentPage;
    private AppInitBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach(this);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_app_initialization);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

                if (currentPage == 0){
                    //if the first page no previous button
                    //if the first page no next button if forms aren't filled

                    binding.btnPrev.setVisibility(View.INVISIBLE);
                    binding.btnMiddle.setVisibility(View.GONE);

                    //TODO: allow validation of user on filling the forms
                    if (user.Fullname.toString().isEmpty()
                            || user.Age.toString().isEmpty()){
                        binding.btnNext.setEnabled(false);
                    }
                }else if(currentPage == (viewPagerAdapter.getCount() - 1)){
                    //if the last page

                    binding.btnNext.setVisibility(View.INVISIBLE);
                    binding.btnMiddle.setText(R.string.finish);
                    binding.btnNext.setEnabled(true);
                }else {
                    //if any other page

                    binding.btnPrev.setVisibility(View.VISIBLE);
                    binding.btnNext.setVisibility(View.VISIBLE);
                    binding.btnMiddle.setVisibility(View.VISIBLE);
                    binding.btnMiddle.setText(R.string.btn_add_later);
                    binding.btnNext.setEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /*
         * Stop swiping of views using touch
         * */
        binding.viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    /**
     * Go to the next page
     * */
    public void onNext(View view){
        binding.viewPager.setCurrentItem(currentPage + 1);
    }

    /**
     * Go back to the previous page
     * */
    public void onPrev(View view){
        binding.viewPager.setCurrentItem(currentPage - 1);
    }

    /**
     * Finish the registration or move to the next page when choosing add later
     * */
    public void onMiddleBtn(View view){
        if (currentPage == (viewPagerAdapter.getCount() - 1)){
            //if this is the last page.
            presenter.onFinish(user);
        }else {
            //if not then just go to the next page.
            onNext(null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
