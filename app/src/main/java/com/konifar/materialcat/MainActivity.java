package com.konifar.materialcat;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.konifar.materialcat.events.PhotoSearchCallbackEvent;
import com.konifar.materialcat.models.PhotoModel;
import com.konifar.materialcat.models.pojo.Photo;
import com.konifar.materialcat.views.ListLoadingView;
import com.konifar.materialcat.views.adapters.PhotosArrayAdapter;
import com.konifar.materialcat.views.listeners.OnLoadMoreScrollListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import jp.co.recruit_mp.android.widget.HeaderFooterGridView;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.grid_view)
    HeaderFooterGridView gridView;
    @Bind(R.id.loading)
    FrameLayout loading;

    private PhotosArrayAdapter adapter;
    private ListLoadingView mLoadingFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        setSupportActionBar(toolbar);
        initGridView();
        initSwipeRefresh();

        showList(1);
    }

    private void initSwipeRefresh() {
        swipeRefresh.setColorSchemeResources(R.color.theme500);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showList(1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initGridView() {
        adapter = new PhotosArrayAdapter(this);
        mLoadingFooter = new ListLoadingView(this);
        gridView.addFooterView(mLoadingFooter);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Photo photo = adapter.getItem(position);
                PhotoPreviewActivity.start(MainActivity.this, view, photo);
            }
        });
    }

    private void showList(final int page) {
        if (page > 1) mLoadingFooter.switchVisible(true);
        PhotoModel.getInstance(this).getCatPhotos(page);
    }

    private void initListViewScrollListener() {
        gridView.setOnScrollListener(new OnLoadMoreScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                showList(page);
            }
        });
    }


    public void onEvent(PhotoSearchCallbackEvent event) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
            adapter.clear();
        }

        if (event.isSuccess()) {
            adapter.addAll(event.getPhotos());
        }

        if (loading.getVisibility() == View.VISIBLE) {
            initListViewScrollListener();
            loading.setVisibility(View.GONE);
        }

        mLoadingFooter.switchVisible(false);
    }

}
