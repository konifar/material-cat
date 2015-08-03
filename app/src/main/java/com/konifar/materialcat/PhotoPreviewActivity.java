package com.konifar.materialcat;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.konifar.materialcat.models.pojo.Photo;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoPreviewActivity extends Activity {

    private static final String EXTRA_ORIENTATION = "extra_orientation";
    private static final String EXTRA_LEFT = "extra_left";
    private static final String EXTRA_TOP = "extra_top";
    private static final String EXTRA_WIDTH = "extra_width";
    private static final String EXTRA_HEIGHT = "extra_height";

    private static final long ANIMATION_DURATION = 300;
    private static final Interpolator decelerateInterpolator = new DecelerateInterpolator();

    @Bind(R.id.img_preview)
    ImageView imgPreview;

    private int leftDelta;
    private int topDelta;
    private float widthScale;
    private float heightScale;
    private int originalOrientation;

    public static void start(Activity activity, View transitionView, Photo photo) {
        Intent intent = new Intent(activity, PhotoPreviewActivity.class);

        int[] screenLocation = new int[2];
        transitionView.getLocationOnScreen(screenLocation);
        int orientation = activity.getResources().getConfiguration().orientation;

        intent.putExtra(Photo.class.getSimpleName(), photo);
        intent.putExtra(EXTRA_ORIENTATION, orientation);
        intent.putExtra(EXTRA_LEFT, screenLocation[0]);
        intent.putExtra(EXTRA_TOP, screenLocation[1]);
        intent.putExtra(EXTRA_WIDTH, transitionView.getWidth());
        intent.putExtra(EXTRA_HEIGHT, transitionView.getHeight());
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        final Photo photo = (Photo) bundle.getSerializable(Photo.class.getSimpleName());
        final int thumbnailTop = bundle.getInt(EXTRA_TOP);
        final int thumbnailLeft = bundle.getInt(EXTRA_LEFT);
        final int thumbnailWidth = bundle.getInt(EXTRA_WIDTH);
        final int thumbnailHeight = bundle.getInt(EXTRA_HEIGHT);
        originalOrientation = bundle.getInt(EXTRA_ORIENTATION);

        Picasso.with(this)
                .load(photo.getImageUrl())
                .into(imgPreview);

        if (savedInstanceState == null) {
            ViewTreeObserver observer = imgPreview.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    imgPreview.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    imgPreview.getLocationOnScreen(screenLocation);
                    leftDelta = thumbnailLeft - screenLocation[0];
                    topDelta = thumbnailTop - screenLocation[1];
                    widthScale = (float) thumbnailWidth / (imgPreview.getWidth());
                    heightScale = (float) thumbnailHeight / imgPreview.getHeight();

                    startEnterAnimation();

                    return true;
                }
            });
        }
    }

    public void startEnterAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            imgPreview.setPivotX(0);
            imgPreview.setPivotY(0);
            imgPreview.setScaleX(widthScale);
            imgPreview.setScaleY(heightScale);
            imgPreview.setTranslationX(leftDelta);
            imgPreview.setTranslationY(topDelta);
        } else {
            AnimatorProxy proxy = AnimatorProxy.wrap(imgPreview);
            proxy.setPivotX(0);
            proxy.setPivotY(0);
            proxy.setScaleX(widthScale);
            proxy.setScaleY(heightScale);
            proxy.setTranslationX(leftDelta);
            proxy.setTranslationY(topDelta);
        }

        ViewPropertyAnimator.animate(imgPreview)
                .setDuration(ANIMATION_DURATION)
                .scaleX(1).scaleY(1)
                .translationX(0).translationY(0)
                .setInterpolator(decelerateInterpolator);
    }

    public void startExitAnimation() {
        final boolean fadeOut;
        if (getResources().getConfiguration().orientation != originalOrientation) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                imgPreview.setPivotX(imgPreview.getWidth() / 2);
                imgPreview.setPivotY(imgPreview.getHeight() / 2);
            } else {
                AnimatorProxy proxy = AnimatorProxy.wrap(imgPreview);
                proxy.setPivotX(imgPreview.getWidth() / 2);
                proxy.setPivotY(imgPreview.getHeight() / 2);
            }
            leftDelta = 0;
            topDelta = 0;
            fadeOut = true;
        } else {
            fadeOut = false;
        }

        ViewPropertyAnimator.animate(imgPreview)
                .setDuration(ANIMATION_DURATION)
                .scaleX(widthScale).scaleY(heightScale)
                .translationX(leftDelta).translationY(topDelta)
                .setInterpolator(decelerateInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        finish();
                    }
                });

        if (fadeOut) {
            ViewPropertyAnimator.animate(imgPreview).alpha(0);
        }
    }

    @Override
    public void onBackPressed() {
        startExitAnimation();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
