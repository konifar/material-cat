package com.konifar.materialcat;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.konifar.fab_transformation.FabTransformation;
import com.konifar.materialcat.utils.AppUtils;
import com.konifar.materialcat.utils.ShareUtils;
import com.konifar.materialcat.views.ShareBarView;
import com.konifar.materialcat.views.adapters.CatsGridPagerAdappter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {

    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.share_bar)
    ShareBarView shareBar;

    private ActionBarDrawerToggle drawerToggle;
    private boolean isTransforming;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        initNavigationView();
        initTabLayout();
    }

    private void initTabLayout() {
        CatsGridPagerAdappter adapter = new CatsGridPagerAdappter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int itemId = menuItem.getItemId();
                switch (itemId) {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_favorite:
                        AppUtils.showToast(R.string.coming_soon, MainActivity.this);
                        return true;
                    case R.id.nav_settings:
                        AppUtils.showToast(R.string.coming_soon, MainActivity.this);
                        return true;
                }

                return false;
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_link:
                AppUtils.showWebPage(ShareUtils.REPOGITORY_URL, this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @OnClick(R.id.fab)
    void onClickFab() {
        if (fab.getVisibility() == View.VISIBLE && !isTransforming) {
            FabTransformation.with(fab)
                    .setListener(new FabTransformation.OnTransformListener() {
                        @Override
                        public void onStartTransform() {
                            isTransforming = true;
                        }

                        @Override
                        public void onEndTransform() {
                            isTransforming = false;
                        }
                    })
                    .transformTo(shareBar);
        }
    }

    @Override
    public void onBackPressed() {
        if (fab.getVisibility() != View.VISIBLE && !isTransforming) {
            FabTransformation.with(fab)
                    .setListener(new FabTransformation.OnTransformListener() {
                        @Override
                        public void onStartTransform() {
                            isTransforming = true;
                        }

                        @Override
                        public void onEndTransform() {
                            isTransforming = false;
                        }
                    })
                    .transformFrom(shareBar);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (fab.getVisibility() != View.VISIBLE && !isTransforming) {
            FabTransformation.with(fab)
                    .setListener(new FabTransformation.OnTransformListener() {
                        @Override
                        public void onStartTransform() {
                            isTransforming = true;
                        }

                        @Override
                        public void onEndTransform() {
                            isTransforming = false;
                        }
                    })
                    .transformFrom(shareBar);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
