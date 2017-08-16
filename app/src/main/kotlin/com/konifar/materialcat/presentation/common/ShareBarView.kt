package com.konifar.materialcat.presentation.common

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.UiShareBarBinding
import com.konifar.materialcat.utils.AppUtils
import com.konifar.materialcat.utils.ShareUtils

class ShareBarView : LinearLayout {

    lateinit var binding: UiShareBarBinding

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        binding = DataBindingUtil.inflate<UiShareBarBinding>(LayoutInflater.from(context), R.layout.ui_share_bar, this, true)
        binding.btnEmail.setOnClickListener { onClickBtnEmail() }
        binding.btnCopy.setOnClickListener { onClickBtnCopy() }
        binding.btnGooglePlus.setOnClickListener { onClickBtnGooglePlus() }
        binding.btnFacebook.setOnClickListener { onClickBtnFacebook() }
        binding.btnTwitter.setOnClickListener { onClickTwitter() }
    }


    internal fun onClickBtnEmail() {
        val mails = arrayOfNulls<String>(1)
        AppUtils.openMailChooser(context, ShareUtils.REPOGITORY_URL, mails,
                context.getString(R.string.app_name))
    }

    internal fun onClickBtnCopy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            val clip = ClipData.newPlainText("simple text", ShareUtils.REPOGITORY_URL)
            AppUtils.showToast(R.string.copyed, context)
        } else {
            AppUtils.showToast(R.string.not_supported, context)
        }
    }

    internal fun onClickBtnGooglePlus() {
        // TODO
        AppUtils.showToast(R.string.coming_soon, context)
    }

    internal fun onClickBtnFacebook() {
        ShareUtils.showShareDialog(context as Activity)
    }

    internal fun onClickTwitter() {
        // TODO
        AppUtils.showToast(R.string.coming_soon, context)
    }

}
