package com.konifar.materialcat.views.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.konifar.materialcat.R;
import com.konifar.materialcat.network.FlickrApiService;
import com.konifar.materialcat.views.fragments.CatsGridFragment;

import java.util.ArrayList;
import java.util.List;

public class CatsGridPagerAdappter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public CatsGridPagerAdappter(FragmentManager fm, Context context) {
        super(fm);
        titles = new ArrayList<>();
        titles.add(context.getString(R.string.popular));
        titles.add(context.getString(R.string.news));

        fragments = new ArrayList<>();
        fragments.add(CatsGridFragment.newInstance(FlickrApiService.Companion.getSORT_INTERESTINGNESS_DESC()));
        fragments.add(CatsGridFragment.newInstance(FlickrApiService.Companion.getSORT_DATE_POSTED_DESC()));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}