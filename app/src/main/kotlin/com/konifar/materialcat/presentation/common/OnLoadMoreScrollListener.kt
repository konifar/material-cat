package com.konifar.materialcat.presentation.common

import android.widget.AbsListView

abstract class OnLoadMoreScrollListener(
        private val innerOnScrollListener: AbsListView.OnScrollListener? = null,
        private val visibleThreshold: Int = 5,
        private var currentPage: Int = 0,
        private val startingPageIndex: Int = 0
) : AbsListView.OnScrollListener {

    private var previousTotalItemCount = 0
    private var loading = true

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

        if (loading && firstVisibleItem > 0 && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
            currentPage++
        }

        if (!loading && firstVisibleItem > 0 && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount) {
            onLoadMore(currentPage + 1, totalItemCount)
            loading = true
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)

    override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
        //
    }

}
