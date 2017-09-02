package com.konifar.materialcat.presentation.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.konifar.fab_transformation.FabTransformation
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.ActivityMainBinding
import com.konifar.materialcat.presentation.gallery.GalleryFragment
import com.konifar.materialcat._extension.AppUtils
import com.konifar.materialcat._extension.ShareUtils
import java.util.*

class MainActivity : AppCompatActivity(), AbsListView.OnScrollListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerToggle: ActionBarDrawerToggle

    private var isTransforming: Boolean = false
    private val callbackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        FacebookSdk.sdkInitialize(applicationContext)

        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener { onClickFab() }

        initNavigationView()
        initTabLayout()
    }

    private fun initTabLayout() {
        val adapter = CatsGridPagerAdappter(supportFragmentManager, this)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun initNavigationView() {
        binding.navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawers()

            val itemId = menuItem.itemId
            when (itemId) {
                R.id.nav_home -> return@OnNavigationItemSelectedListener true
                R.id.nav_favorite -> {
                    AppUtils.showToast(R.string.coming_soon, this@MainActivity)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_settings -> {
                    AppUtils.showToast(R.string.coming_soon, this@MainActivity)
                    return@OnNavigationItemSelectedListener true
                }
            }

            false
        })

        drawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_open, R.string.drawer_close)
                .apply { isDrawerIndicatorEnabled = true }
        binding.drawerLayout.setDrawerListener(drawerToggle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            R.id.action_link -> {
                AppUtils.showWebPage(ShareUtils.REPOGITORY_URL, this)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    internal fun onClickFab() {
        if (binding.fab.visibility == View.VISIBLE && !isTransforming) {
            FabTransformation.with(binding.fab)
                    .setListener(object : FabTransformation.OnTransformListener {
                        override fun onStartTransform() {
                            isTransforming = true
                        }

                        override fun onEndTransform() {
                            isTransforming = false
                        }
                    })
                    .transformTo(binding.shareBar!!)
        }
    }

    override fun onBackPressed() {
        if (binding.fab.visibility != View.VISIBLE && !isTransforming) {
            FabTransformation.with(binding.fab)
                    .setListener(object : FabTransformation.OnTransformListener {
                        override fun onStartTransform() {
                            isTransforming = true
                        }

                        override fun onEndTransform() {
                            isTransforming = false
                        }
                    })
                    .transformFrom(binding.shareBar)
            return
        }
        super.onBackPressed()
    }

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
        //
    }

    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (binding.fab.visibility != View.VISIBLE && !isTransforming) {
            FabTransformation.with(binding.fab)
                    .setListener(object : FabTransformation.OnTransformListener {
                        override fun onStartTransform() {
                            isTransforming = true
                        }

                        override fun onEndTransform() {
                            isTransforming = false
                        }
                    })
                    .transformFrom(binding.shareBar)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private class CatsGridPagerAdappter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {

        private val fragments: MutableList<Fragment>
        private val titles: MutableList<String>

        init {
            titles = ArrayList<String>()
            titles.add(context.getString(R.string.popular))
            titles.add(context.getString(R.string.news))

            fragments = ArrayList<Fragment>()
            fragments.add(GalleryFragment.newInstance(GalleryFragment.Type.POPULAR))
            fragments.add(GalleryFragment.newInstance(GalleryFragment.Type.NEW))
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

}
