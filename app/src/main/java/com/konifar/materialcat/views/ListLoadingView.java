package com.konifar.materialcat.views;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.konifar.materialcat.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListLoadingView extends LinearLayout {

    @Bind(R.id.list_loading)
    FrameLayout mListLoading;

    public ListLoadingView(Context context) {
        super(context);
        inflate(context, R.layout.ui_list_loading, this);
        ButterKnife.bind(this);
    }

    public void switchVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mListLoading.setVisibility(visibility);
    }

}
