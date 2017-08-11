package com.konifar.materialcat.views

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import butterknife.Bind
import butterknife.ButterKnife
import com.konifar.materialcat.R

class ListLoadingView(context: Context) : LinearLayout(context) {

    @Bind(R.id.list_loading)
    internal var mListLoading: FrameLayout? = null

    init {
        View.inflate(context, R.layout.ui_list_loading, this)
        ButterKnife.bind(this)
    }

    fun switchVisible(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        mListLoading!!.visibility = visibility
    }

}
