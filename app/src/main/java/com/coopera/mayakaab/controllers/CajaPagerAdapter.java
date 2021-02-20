package com.coopera.mayakaab.controllers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.coopera.mayakaab.views.CajaFragment;
import com.coopera.mayakaab.views.MielConvencionalFragment;
import com.coopera.mayakaab.views.MielOrganicaFragment;
import com.coopera.mayakaab.views.MovimientosFragment;

public class CajaPagerAdapter extends FragmentStatePagerAdapter {

    private int noOfTabs;
    public CajaPagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.noOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CajaFragment();
            case 1:
                return new MovimientosFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
