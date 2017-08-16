package com.konifar.materialcat.presentation.common

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.support.v7.graphics.Palette.Builder
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.UiColorPalleteBinding

class ColorPalleteView : LinearLayout {

    lateinit var binding: UiColorPalleteBinding

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        binding = DataBindingUtil.inflate<UiColorPalleteBinding>(LayoutInflater.from(context), R.layout.ui_color_pallete, this, true)
    }

    fun initColors(bitmap: Bitmap) {
        Builder(bitmap).generate { palette ->
            val vibrantSwatch = palette.vibrantSwatch
            if (vibrantSwatch != null) {
                binding.containerVibrant.setBackgroundColor(vibrantSwatch.rgb)
                binding.txtVibrant.setTextColor(vibrantSwatch.titleTextColor)
                binding.txtVibrant.text = getHexString(vibrantSwatch.rgb)
            }

            val lightVibrantSwatch = palette.lightVibrantSwatch
            if (lightVibrantSwatch != null) {
                binding.containerLigntVibrant.setBackgroundColor(lightVibrantSwatch.rgb)
                binding.txtLigntVibrant.setTextColor(lightVibrantSwatch.titleTextColor)
                binding.txtLigntVibrant!!.text = getHexString(lightVibrantSwatch.rgb)
            }

            val darkVibrantSwatch = palette.darkVibrantSwatch
            if (darkVibrantSwatch != null) {
                binding.containerDarkVibrant.setBackgroundColor(darkVibrantSwatch.rgb)
                binding.txtDarkVibrant.setTextColor(darkVibrantSwatch.titleTextColor)
                binding.txtDarkVibrant.text = getHexString(darkVibrantSwatch.rgb)
            }

            if (vibrantSwatch == null && lightVibrantSwatch == null && darkVibrantSwatch == null) {
                binding.rowVibrant.visibility = View.GONE
            }

            val mutedSwatch = palette.mutedSwatch
            if (mutedSwatch != null) {
                binding.containerMuted.setBackgroundColor(mutedSwatch.rgb)
                binding.txtMuted.setTextColor(mutedSwatch.titleTextColor)
                binding.txtMuted.text = getHexString(mutedSwatch.rgb)
            }

            val lightMutedSwatch = palette.lightMutedSwatch
            if (lightMutedSwatch != null) {
                binding.containerLigntMuted.setBackgroundColor(lightMutedSwatch.rgb)
                binding.txtLigntMuted.setTextColor(lightMutedSwatch.titleTextColor)
                binding.txtLigntMuted.text = getHexString(lightMutedSwatch.rgb)
            }

            val darkMutedSwatch = palette.darkMutedSwatch
            if (darkMutedSwatch != null) {
                binding.containerDarkMuted.setBackgroundColor(darkMutedSwatch.rgb)
                binding.txtDarkMuted.setTextColor(darkMutedSwatch.titleTextColor)
                binding.txtDarkMuted.text = getHexString(darkMutedSwatch.rgb)
            }

            if (mutedSwatch == null && lightMutedSwatch == null && darkMutedSwatch == null) {
                binding.rowMuted.visibility = View.GONE
            }
        }
    }

    private fun getHexString(rgb: Int): String {
        return HEX_PREFIX + Integer.toHexString(rgb)
    }

    companion object {

        private val HEX_PREFIX = "#"
    }

}
