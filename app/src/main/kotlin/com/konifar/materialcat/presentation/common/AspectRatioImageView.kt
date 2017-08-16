package com.konifar.materialcat.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.konifar.materialcat.R

class AspectRatioImageView : ImageView {

    private var ratio: Float = 0.toFloat()
    private var autoScale: Boolean = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
        try {
            ratio = a.getFloat(R.styleable.AspectRatioImageView_imageRatio, DEFAULT_RATIO)
            autoScale = a.getBoolean(R.styleable.AspectRatioImageView_autoScale, false)
        } finally {
            a.recycle()
        }
    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (autoScale) {
            val drawable = drawable
            if (drawable != null) {
                val width = View.MeasureSpec.getSize(widthMeasureSpec)
                val height = Math.ceil((width.toFloat() * drawable.intrinsicHeight.toFloat() / drawable.intrinsicWidth.toFloat()).toDouble()).toInt()
                setMeasuredDimension(width, height)
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }

        } else {
            val parentWidth = View.MeasureSpec.getSize(widthMeasureSpec)
            val propotionalHeight = (parentWidth / ratio).toDouble()

            if (propotionalHeight < suggestedMinimumHeight) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            } else {
                setMeasuredDimension(parentWidth, propotionalHeight.toInt())
            }
        }
    }

    companion object {

        private val DEFAULT_RATIO = 1.618f
    }

}
