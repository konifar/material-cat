package com.konifar.materialcat.views;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.konifar.materialcat.R;
import com.konifar.materialcat.utils.AppUtils;
import com.konifar.materialcat.utils.ShareUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareBarView extends LinearLayout {

    public ShareBarView(Context context) {
        super(context);
    }

    public ShareBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.ui_share_bar, this);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_email)
    void onClickBtnEmail() {
        String[] mails = new String[1];
        AppUtils.openMailChooser(getContext(), ShareUtils.REPOGITORY_URL, mails,
                getContext().getString(R.string.app_name));
    }

    @OnClick(R.id.btn_copy)
    void onClickBtnCopy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipData clip = ClipData.newPlainText("simple text", ShareUtils.REPOGITORY_URL);
            AppUtils.showToast(R.string.copyed, getContext());
        } else {
            AppUtils.showToast(R.string.not_supported, getContext());
        }
    }

    @OnClick(R.id.btn_google_plus)
    void onClickBtnGooglePlus() {
        // TODO
        AppUtils.showToast(R.string.coming_soon, getContext());
    }

    @OnClick(R.id.btn_facebook)
    void onClickBtnFacebook() {
        ShareUtils.showShareDialog((Activity) getContext());
    }

    @OnClick(R.id.btn_twitter)
    void onClickTwitter() {
        // TODO
        AppUtils.showToast(R.string.coming_soon, getContext());
    }

}
