package com.konifar.materialcat.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.konifar.materialcat.R;
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
        //
    }

    @OnClick(R.id.btn_copy)
    void onClickBtnCopy() {
        //
    }

    @OnClick(R.id.btn_google_plus)
    void onClickBtnGooglePlus() {
        //
    }

    @OnClick(R.id.btn_facebook)
    void onClickBtnFacebook() {
        ShareUtils.showShareDialog((Activity) getContext());
    }

    @OnClick(R.id.btn_twitter)
    void onClickTwitter() {
        //
    }

}
