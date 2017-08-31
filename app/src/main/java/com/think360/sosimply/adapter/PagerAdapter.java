package com.think360.sosimply.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by think360 on 19/04/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentSparseArray = new ArrayList<>();

    public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentSparseArray) {
        super(fm);
        this.fragmentSparseArray = fragmentSparseArray;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentSparseArray.get(position);
    }

    @Override
    public int getCount() {
        return fragmentSparseArray.size();
    }
}
