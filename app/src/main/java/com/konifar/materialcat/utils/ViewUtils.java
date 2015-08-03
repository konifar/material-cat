package com.konifar.materialcat.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

public class ViewUtils {

    private static final String TAG = ViewUtils.class.getSimpleName();
    private static final int IMAGE_FADE_DURATION_MILLS = 200;

    private static ViewUtils instance;

    private ViewUtils() {
    }

    public static ViewUtils getInstance() {
        if (instance == null) {
            instance = new ViewUtils();
        }
        return instance;
    }

    public void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

}
