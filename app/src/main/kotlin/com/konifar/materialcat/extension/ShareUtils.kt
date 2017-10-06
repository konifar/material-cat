package com.konifar.materialcat.extension

import android.app.Activity
import android.net.Uri

import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.konifar.materialcat.R

object ShareUtils {

    val REPOGITORY_URL = "https://github.com/konifar/material-cat"

    fun showShareDialog(activity: Activity) {
        if (!ShareDialog.canShow(ShareLinkContent::class.java)) return

        var builder = ShareLinkContent.Builder()
        builder = builder.setContentUrl(Uri.parse(REPOGITORY_URL))
                .setContentTitle(activity.getString(R.string.app_name))
        ShareDialog.show(activity, builder.build())
    }

}
