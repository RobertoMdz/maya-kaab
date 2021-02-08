package com.coopera.mayakaab.controllers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.coopera.mayakaab.views.MielConvencionalFragment;
import com.coopera.mayakaab.views.MielOrganicaFragment;

public class MielPagerAdapter extends FragmentStatePagerAdapter {

    private int noOfTabs;
    public MielPagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MielConvencionalFragment();
            case 1:
                return new MielOrganicaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
