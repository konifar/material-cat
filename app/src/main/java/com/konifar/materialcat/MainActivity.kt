package com.konifar.materialcat

import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
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
import com.konifar.materialcat.databinding.ActivityMainBinding
import com.konifar.materialcat.utils.AppUtils
import com.konifar.materialcat.utils.ShareUtils
import com.konifar.materialcat.views.adapters.CatsGridPagerAdappter

class MainActivity : AppCompatActivity(), AbsListView.OnScrollListener {

    lateinit var binding: ActivityMainBinding

    private var drawerToggle: ActionBarDrawerToggle? = null
    private var isTransforming: Boolean = false
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        FacebookSdk.sdkInitialize(applicationContext)
        callbackManager = CallbackManager.Factory.create()

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
        drawerToggle!!.isDrawerIndicatorEnabled = true
        binding.drawerLayout.setDrawerListener(drawerToggle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle!!.onOptionsItemSelected(item)) {
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
        drawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle!!.onConfigurationChanged(newConfig)
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
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
