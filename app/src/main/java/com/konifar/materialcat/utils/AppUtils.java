package com.konifar.materialcat.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.konifar.materialcat.R;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    private static final String TAG = AppUtils.class.getName();
    private static final String PACKAGE_MMS = "mms";
    private static final String PACKAGE_GMAIL = "android.gm";
    private static final String PACKAGE_EMAIL = "android.email";
    private static final String INTENT_TYPE_MSG = "message/rfc822";
    private static final String INTENT_TYPE_TEXT = "text/plain";

    public static void showToast(String message, Context context, int duration) {
        if (message == null || message.length() == 0) return;

        try {
            Toast toast = Toast.makeText(context, message, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            //
        }
    }

    public static void showToast(int stringResId, Context context, int duration) {
        showToast(context.getResources().getString(stringResId), context, duration);
    }

    public static void showToast(String message, Context context) {
        showToast(message, context, Toast.LENGTH_LONG);
    }

    public static void showToast(int stringResId, Context context) {
        showToast(context.getResources().getString(stringResId), context);
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return context.getString(R.string.version_prefix, packageInfo.versionName);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
            return "";
        }
    }

    public static void openMailChooser(Context context, String text, String[] mails, String subject) {
        Intent mailIntent = new Intent();
        mailIntent.setAction(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_TEXT, text);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, mails);
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.setType(INTENT_TYPE_MSG);

        PackageManager pm = context.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType(INTENT_TYPE_TEXT);

        Intent openInChooser = Intent.createChooser(mailIntent, "");

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (ResolveInfo ri : resInfo) {
            String packageName = ri.activityInfo.packageName;
            if (packageName.contains(PACKAGE_EMAIL)) {
                mailIntent.setPackage(packageName);
            } else if (packageName.contains(PACKAGE_MMS) || packageName.contains(PACKAGE_GMAIL)) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType(INTENT_TYPE_TEXT);
                if (packageName.contains(PACKAGE_MMS)) {
                    intent.putExtra("subject", subject);
                    intent.putExtra("sms_body", text);
                    intent.putExtra("address", mails[0]);
                    intent.setType(INTENT_TYPE_MSG);
                } else if (packageName.contains(PACKAGE_GMAIL)) {
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_EMAIL, mails);
                    intent.setType(INTENT_TYPE_MSG);
                }

                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        context.startActivity(openInChooser);
    }

    public static void showWebPage(String url, Context context) {
        if (TextUtils.isEmpty(url)) return;

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static String getGooglePlayUrl(String utmSource, String utmContent) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("play.google.com");
        builder.path("/store/apps/details");
        builder.appendQueryParameter("id", "com.konifar.materialcat");

        if (utmSource != null && utmContent != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("utm_source=");
            sb.append(utmSource);
            sb.append("&utm_medium=direct");
            sb.append("&");
            sb.append("utm_content=");
            sb.append(utmContent);
            builder.appendQueryParameter("referrer", sb.toString());
        }

        return builder.build().toString();
    }

}
