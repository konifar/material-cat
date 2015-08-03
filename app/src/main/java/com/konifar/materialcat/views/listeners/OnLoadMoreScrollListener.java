package com.konifar.materialcat.views.listeners;

import android.widget.AbsListView;

public abstract class OnLoadMoreScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;
    private AbsListView.OnScrollListener innerOnScrollListener;

    public OnLoadMoreScrollListener() {
        //
    }

    public OnLoadMoreScrollListener(AbsListView.OnScrollListener innerOnScrollListener) {
        this.innerOnScrollListener = innerOnScrollListener;
    }

    public OnLoadMoreScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public OnLoadMoreScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    public int getItemCountOffset() {
        return 0;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (innerOnScrollListener != null) {
            innerOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        totalItemCount -= getItemCountOffset();
        totalItemCount = Math.max(0, totalItemCount);

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            onLoadMore(currentPage + 1, totalItemCount);
            loading = true;
        }
    }

    public abstract void onLoadMore(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //
    }

}
