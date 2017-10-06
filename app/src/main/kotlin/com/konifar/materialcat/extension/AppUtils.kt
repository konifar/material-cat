package com.konifar.materialcat.extension

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LabeledIntent
import android.net.Uri
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import java.util.*

object AppUtils {

    private val TAG = AppUtils::class.java.name
    private val PACKAGE_MMS = "mms"
    private val PACKAGE_GMAIL = "android.gm"
    private val PACKAGE_EMAIL = "android.email"
    private val INTENT_TYPE_MSG = "message/rfc822"
    private val INTENT_TYPE_TEXT = "text/plain"

    @JvmOverloads fun showToast(message: String?, context: Context, duration: Int = Toast.LENGTH_LONG) {
        if (message == null || message.length == 0) return

        try {
            val toast = Toast.makeText(context, message, duration)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        } catch (e: Exception) {
            //
        }

    }

    fun showToast(stringResId: Int, context: Context, duration: Int) {
        showToast(context.resources.getString(stringResId), context, duration)
    }

    fun showToast(stringResId: Int, context: Context) {
        showToast(context.resources.getString(stringResId), context)
    }

    fun openMailChooser(context: Context, text: String, mails: Array<String?>, subject: String) {
        val mailIntent = Intent()
        mailIntent.action = Intent.ACTION_SEND
        mailIntent.putExtra(Intent.EXTRA_TEXT, text)
        mailIntent.putExtra(Intent.EXTRA_EMAIL, mails)
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mailIntent.type = INTENT_TYPE_MSG

        val pm = context.packageManager
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = INTENT_TYPE_TEXT

        val openInChooser = Intent.createChooser(mailIntent, "")

        val resInfo = pm.queryIntentActivities(sendIntent, 0)
        val intentList = ArrayList<LabeledIntent>()
        for (ri in resInfo) {
            val packageName = ri.activityInfo.packageName
            if (packageName.contains(PACKAGE_EMAIL)) {
                mailIntent.`package` = packageName
            } else if (packageName.contains(PACKAGE_MMS) || packageName.contains(PACKAGE_GMAIL)) {
                val intent = Intent()
                intent.component = ComponentName(packageName, ri.activityInfo.name)
                intent.action = Intent.ACTION_SEND
                intent.type = INTENT_TYPE_TEXT
                if (packageName.contains(PACKAGE_MMS)) {
                    intent.putExtra("subject", subject)
                    intent.putExtra("sms_body", text)
                    intent.putExtra("address", mails[0])
                    intent.type = INTENT_TYPE_MSG
                } else if (packageName.contains(PACKAGE_GMAIL)) {
                    intent.putExtra(Intent.EXTRA_TEXT, text)
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    intent.putExtra(Intent.EXTRA_EMAIL, mails)
                    intent.type = INTENT_TYPE_MSG
                }

                intentList.add(LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon))
            }
        }

        val extraIntents = intentList.toTypedArray()

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents)
        context.startActivity(openInChooser)
    }

    fun showWebPage(url: String, context: Context) {
        if (TextUtils.isEmpty(url)) return

        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }

}
