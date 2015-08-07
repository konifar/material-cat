package com.konifar.materialcat.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.konifar.materialcat.R;

public class CustomKenBurnsView extends KenBurnsView {

    private static final float DEFAULT_RATIO = 1.618f;

    private float ratio;
    private boolean autoScale;

    public CustomKenBurnsView(Context context) {
        super(context);
    }

    public CustomKenBurnsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);
        try {
            ratio = a.getFloat(R.styleable.AspectRatioImageView_imageRatio, DEFAULT_RATIO);
            autoScale = a.getBoolean(R.styleable.AspectRatioImageView_autoScale, false);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (autoScale) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) Math.ceil((float) width
                        * (float) drawable.getIntrinsicHeight() / (float) drawable.getIntrinsicWidth());
                setMeasuredDimension(width, height);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }

        } else {
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            double propotionalHeight = parentWidth / ratio;

            if (propotionalHeight < getSuggestedMinimumHeight()) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                setMeasuredDimension(parentWidth, (int) propotionalHeight);
            }
        }
    }
}
