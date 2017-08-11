package com.konifar.materialcat.views

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.graphics.Palette.Builder
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.Bind
import butterknife.ButterKnife
import com.konifar.materialcat.R

class ColorPalleteView : LinearLayout {

    @Bind(R.id.row_vibrant)
    internal var rowVibrant: View? = null
    @Bind(R.id.row_muted)
    internal var rowMuted: View? = null
    @Bind(R.id.container_lignt_vibrant)
    internal var containerLightVibrant: View? = null
    @Bind(R.id.container_vibrant)
    internal var containerVibrant: View? = null
    @Bind(R.id.container_dark_vibrant)
    internal var containerDarkVibrant: View? = null
    @Bind(R.id.container_lignt_muted)
    internal var containerLightMuted: View? = null
    @Bind(R.id.container_muted)
    internal var containerMuted: View? = null
    @Bind(R.id.container_dark_muted)
    internal var containerDarkMuted: View? = null
    @Bind(R.id.txt_lignt_vibrant)
    internal var txtLightVibrant: TextView? = null
    @Bind(R.id.txt_vibrant)
    internal var txtVibrant: TextView? = null
    @Bind(R.id.txt_dark_vibrant)
    internal var txtDarkVibrant: TextView? = null
    @Bind(R.id.txt_lignt_muted)
    internal var txtLightMuted: TextView? = null
    @Bind(R.id.txt_muted)
    internal var txtMuted: TextView? = null
    @Bind(R.id.txt_dark_muted)
    internal var txtDarkMuted: TextView? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        View.inflate(context, R.layout.ui_color_pallete, this)
        ButterKnife.bind(this)
    }

    fun initColors(bitmap: Bitmap) {
        Builder(bitmap).generate { palette ->
            val vibrantSwatch = palette.vibrantSwatch
            if (vibrantSwatch != null) {
                containerVibrant!!.setBackgroundColor(vibrantSwatch.rgb)
                txtVibrant!!.setTextColor(vibrantSwatch.titleTextColor)
                txtVibrant!!.text = getHexString(vibrantSwatch.rgb)
            }

            val lightVibrantSwatch = palette.lightVibrantSwatch
            if (lightVibrantSwatch != null) {
                containerLightVibrant!!.setBackgroundColor(lightVibrantSwatch.rgb)
                txtLightVibrant!!.setTextColor(lightVibrantSwatch.titleTextColor)
                txtLightVibrant!!.text = getHexString(lightVibrantSwatch.rgb)
            }

            val darkVibrantSwatch = palette.darkVibrantSwatch
            if (darkVibrantSwatch != null) {
                containerDarkVibrant!!.setBackgroundColor(darkVibrantSwatch.rgb)
                txtDarkVibrant!!.setTextColor(darkVibrantSwatch.titleTextColor)
                txtDarkVibrant!!.text = getHexString(darkVibrantSwatch.rgb)
            }

            if (vibrantSwatch == null && lightVibrantSwatch == null && darkVibrantSwatch == null) {
                rowVibrant!!.visibility = View.GONE
            }

            val mutedSwatch = palette.mutedSwatch
            if (mutedSwatch != null) {
                containerMuted!!.setBackgroundColor(mutedSwatch.rgb)
                txtMuted!!.setTextColor(mutedSwatch.titleTextColor)
                txtMuted!!.text = getHexString(mutedSwatch.rgb)
            }

            val lightMutedSwatch = palette.lightMutedSwatch
            if (lightMutedSwatch != null) {
                containerLightMuted!!.setBackgroundColor(lightMutedSwatch.rgb)
                txtLightMuted!!.setTextColor(lightMutedSwatch.titleTextColor)
                txtLightMuted!!.text = getHexString(lightMutedSwatch.rgb)
            }

            val darkMutedSwatch = palette.darkMutedSwatch
            if (darkMutedSwatch != null) {
                containerDarkMuted!!.setBackgroundColor(darkMutedSwatch.rgb)
                txtDarkMuted!!.setTextColor(darkMutedSwatch.titleTextColor)
                txtDarkMuted!!.text = getHexString(darkMutedSwatch.rgb)
            }

            if (mutedSwatch == null && lightMutedSwatch == null && darkMutedSwatch == null) {
                rowMuted!!.visibility = View.GONE
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
