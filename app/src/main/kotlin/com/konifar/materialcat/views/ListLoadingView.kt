package com.konifar.materialcat.views

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.UiListLoadingBinding

class ListLoadingView(context: Context) : LinearLayout(context) {

    var binding: UiListLoadingBinding

    init {
        binding = DataBindingUtil.inflate<UiListLoadingBinding>(LayoutInflater.from(context), R.layout.ui_list_loading, this, true)
    }

    fun switchVisible(visible: Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE
        binding.listLoading.visibility = visibility
    }

}
