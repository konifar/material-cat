package com.konifar.materialcat.presentation.common.customview

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.UiPhotoInfoBinding

class PhotoInfoView : FrameLayout {

    lateinit var binding: UiPhotoInfoBinding

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.ui_photo_info, this, true)

        val a = context.obtainStyledAttributes(attrs, R.styleable.PhotoInfoView)
        try {
            val iconSrc = a.getDrawable(R.styleable.PhotoInfoView_iconSrc)
            val titleText = a.getString(R.styleable.PhotoInfoView_titleText)
            val subText = a.getString(R.styleable.PhotoInfoView_subText)

            bindData(iconSrc, titleText, subText)
        } finally {
            a.recycle()
        }
    }

    private fun bindData(iconSrc: Drawable?, titleText: String?, subText: String?) {
        if (iconSrc != null) binding.imgIcon.setImageDrawable(iconSrc)
        if (titleText != null) binding.txtTitle.text = titleText
        if (subText != null) binding.txtSub.text = subText
    }

    fun setTitleText(text: String) {
        binding.txtTitle.text = text
    }

}
