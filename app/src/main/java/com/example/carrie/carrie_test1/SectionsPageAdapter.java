package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/8.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
public class SectionsPageAdapter extends FragmentPagerAdapter{
    private final List<Fragment> mFragmentList = new ArrayList<>();


    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
