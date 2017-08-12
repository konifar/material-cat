package com.konifar.materialcat.views.listeners

import android.widget.AbsListView

abstract class OnLoadMoreScrollListener : AbsListView.OnScrollListener {

    private var visibleThreshold = 5
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private var startingPageIndex = 0
    private var innerOnScrollListener: AbsListView.OnScrollListener? = null

    constructor() {
        //
    }

    constructor(innerOnScrollListener: AbsListView.OnScrollListener) {
        this.innerOnScrollListener = innerOnScrollListener
    }

    constructor(visibleThreshold: Int) {
        this.visibleThreshold = visibleThreshold
    }

    constructor(visibleThreshold: Int, startPage: Int) {
        this.visibleThreshold = visibleThreshold
        this.startingPageIndex = startPage
        this.currentPage = startPage
    }

    val itemCountOffset: Int
        get() = 0

    override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        var totalItemCount = totalItemCount
        innerOnScrollListener?.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount)

        totalItemCount -= itemCountOffset
        totalItemCount = Math.max(0, totalItemCount)

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
            currentPage++
        }

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            onLoadMore(currentPage + 1, totalItemCount)
            loading = true
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
        //
    }

}
