package com.konifar.materialcat.views.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.konifar.materialcat.R
import com.konifar.materialcat.network.FlickrApiService
import com.konifar.materialcat.views.fragments.CatsGridFragment
import java.util.*

class CatsGridPagerAdappter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {

    private val fragments: MutableList<Fragment>
    private val titles: MutableList<String>

    init {
        titles = ArrayList<String>()
        titles.add(context.getString(R.string.popular))
        titles.add(context.getString(R.string.news))

        fragments = ArrayList<Fragment>()
        fragments.add(CatsGridFragment.newInstance(FlickrApiService.SORT_INTERESTINGNESS_DESC))
        fragments.add(CatsGridFragment.newInstance(FlickrApiService.SORT_DATE_POSTED_DESC))
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}