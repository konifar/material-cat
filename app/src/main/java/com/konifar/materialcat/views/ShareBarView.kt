package com.konifar.materialcat.views

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import butterknife.ButterKnife
import butterknife.OnClick
import com.konifar.materialcat.R
import com.konifar.materialcat.utils.AppUtils
import com.konifar.materialcat.utils.ShareUtils

class ShareBarView : LinearLayout {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        View.inflate(context, R.layout.ui_share_bar, this)
        ButterKnife.bind(this)
    }


    @OnClick(R.id.btn_email)
    internal fun onClickBtnEmail() {
        val mails = arrayOfNulls<String>(1)
        AppUtils.openMailChooser(context, ShareUtils.REPOGITORY_URL, mails,
                context.getString(R.string.app_name))
    }

    @OnClick(R.id.btn_copy)
    internal fun onClickBtnCopy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            val clip = ClipData.newPlainText("simple text", ShareUtils.REPOGITORY_URL)
            AppUtils.showToast(R.string.copyed, context)
        } else {
            AppUtils.showToast(R.string.not_supported, context)
        }
    }

    @OnClick(R.id.btn_google_plus)
    internal fun onClickBtnGooglePlus() {
        // TODO
        AppUtils.showToast(R.string.coming_soon, context)
    }

    @OnClick(R.id.btn_facebook)
    internal fun onClickBtnFacebook() {
        ShareUtils.showShareDialog(context as Activity)
    }

    @OnClick(R.id.btn_twitter)
    internal fun onClickTwitter() {
        // TODO
        AppUtils.showToast(R.string.coming_soon, context)
    }

}
