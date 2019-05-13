package com.check.wq.checkapp.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.check.wq.checkapp.Adapter.HomeFragmentPagerAdapter;
import com.check.wq.checkapp.R;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener{
    private HomeFragmentPagerAdapter mAdapter;
    private ViewPager vpager;
    private RadioButton tab_btn_home, tab_btn_home1;
    private RadioButton tab_btn_msg, tab_btn_my1;
    private RadioButton tab_btn_my, tab_btn_msg1;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        tab_btn_home.setChecked(true);
        //Viewpager设置成3个缓存
        vpager.setOffscreenPageLimit(3);
    }

    private void bindViews() {
        RadioGroup rg_tab_bar = findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        tab_btn_msg = findViewById(R.id.tab_btn_msg);
        tab_btn_my = findViewById(R.id.tab_btn_my);
        tab_btn_home = findViewById(R.id.tab_btn_home);
        vpager = findViewById(R.id.ly_content);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
        tab_btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                tab_btn_home.setChecked(true);
            }
        });
        tab_btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                tab_btn_msg.setChecked(true);
            }
        });
        tab_btn_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                tab_btn_my.setChecked(true);
            }
        });
    }

    @Override
    public void onPageScrolled( int i, float v, int i1 ) {

    }

    @Override
    public void onPageSelected( int i ) {

    }
    @Override
    public void onCheckedChanged( RadioGroup group, int checkedId ) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        switch (checkedId) {

            case R.id.tab_btn_home:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case (R.id.tab_btn_msg):
                vpager.setCurrentItem(PAGE_TWO);

                break;
            case (R.id.tab_btn_my):

                vpager.setCurrentItem(PAGE_THREE);
                break;
        }
        beginTransaction.commit();
    }
    @Override
    public void onPageScrollStateChanged( int state ) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:

                    tab_btn_home.setChecked(true);
                    break;
                case PAGE_TWO:
                    tab_btn_msg.setChecked(true);
                    break;
                case PAGE_THREE:
                    tab_btn_my.setChecked(true);
                    break;
            }
        }
    }


}
