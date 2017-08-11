package com.konifar.materialcat.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.konifar.materialcat.R

class PhotoInfoView : FrameLayout {

    @Bind(R.id.img_icon)
    internal var imgIcon: ImageView? = null
    @Bind(R.id.txt_title)
    internal var txtTitle: TextView? = null
    @Bind(R.id.txt_sub)
    internal var txtSub: TextView? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        View.inflate(context, R.layout.ui_photo_info, this)
        ButterKnife.bind(this)

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
        if (iconSrc != null) imgIcon!!.setImageDrawable(iconSrc)
        if (titleText != null) txtTitle!!.text = titleText
        if (subText != null) txtSub!!.text = subText
    }

    fun setTitleText(text: String) {
        txtTitle!!.text = text
    }

}
