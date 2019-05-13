package com.check.wq.checkapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.check.wq.checkapp.Activity.MainActivity;
import com.check.wq.checkapp.Fragment.CheckFragment;
import com.check.wq.checkapp.Fragment.HomeFragment;
import com.check.wq.checkapp.Fragment.SetFragment;

import java.util.List;

public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private final int PAGER_COUNT = 3;
    private HomeFragment mHomeFragment = null;
    private CheckFragment mCheckFragment = null;
    private SetFragment mSetFragment = null;
    private FragmentManager mfragmentManager;
    private List<Fragment> fragments;
    public HomeFragmentPagerAdapter( FragmentManager fm ) {
        super(fm);
        mHomeFragment = new HomeFragment();
        mCheckFragment = new CheckFragment();
        mSetFragment = new SetFragment();
    }

    @Override
    public Fragment getItem( int position ) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = mHomeFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = mCheckFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = mSetFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
    @Override
    public Object instantiateItem( ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);

    }
}
