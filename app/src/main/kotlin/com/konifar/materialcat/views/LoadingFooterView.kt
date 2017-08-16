package com.konifar.materialcat.views

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.ViewLoadingFooterBinding

class LoadingFooterView(context: Context) : FrameLayout(context) {

    val binding: ViewLoadingFooterBinding = DataBindingUtil.inflate<ViewLoadingFooterBinding>(
            LayoutInflater.from(context), R.layout.view_loading_footer, this, true)

    fun switchVisible(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        binding.listLoading.visibility = visibility
    }

}