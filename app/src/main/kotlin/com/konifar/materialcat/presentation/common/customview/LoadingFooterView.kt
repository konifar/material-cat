package com.konifar.materialcat.presentation.common.customview

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.ViewLoadingFooterBinding

class LoadingFooterView(context: Context) : FrameLayout(context) {

    val binding: ViewLoadingFooterBinding = DataBindingUtil.inflate<ViewLoadingFooterBinding>(
            LayoutInflater.from(context), R.layout.view_loading_footer, this, true)

}