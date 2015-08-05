package com.konifar.materialcat;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.konifar.materialcat.models.pojo.Photo;
import com.konifar.materialcat.utils.FabAnimationUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PhotoDetailActivity extends AppCompatActivity {

    private static final String EXTRA_ORIENTATION = "extra_orientation";
    private static final String EXTRA_LEFT = "extra_left";
    private static final String EXTRA_TOP = "extra_top";
    private static final String EXTRA_WIDTH = "extra_width";
    private static final String EXTRA_HEIGHT = "extra_height";

    private static final long ANIMATION_DURATION = 200;
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.img_preview)
    ImageView imgPreview;
    @Bind(R.id.img_preview_dummy)
    ImageView imgPreviewDummy;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private int leftDelta;
    private int topDelta;
    private float widthScale;
    private float heightScale;
    private int originalOrientation;

    public static void start(Activity activity, View transitionView, Photo photo) {
        Intent intent = new Intent(activity, PhotoDetailActivity.class);
        intent.putExtra(Photo.class.getSimpleName(), photo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, transitionView,
                            activity.getString(R.string.shared_element_photo));
            activity.startActivity(intent, options.toBundle());
        } else {
            int[] screenLocation = new int[2];
            transitionView.getLocationOnScreen(screenLocation);
            int orientation = activity.getResources().getConfiguration().orientation;
            intent.putExtra(EXTRA_ORIENTATION, orientation);
            intent.putExtra(EXTRA_LEFT, screenLocation[0]);
            intent.putExtra(EXTRA_TOP, screenLocation[1]);
            intent.putExtra(EXTRA_WIDTH, transitionView.getWidth());
            intent.putExtra(EXTRA_HEIGHT, transitionView.getHeight());
            activity.startActivity(intent);
            activity.overridePendingTransition(0, R.anim.activity_scale_start_exit);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        final Photo photo = (Photo) bundle.getSerializable(Photo.class.getSimpleName());

        Picasso.with(this)
                .load(photo.getImageUrl())
                .placeholder(R.color.grey200)
                .into(imgPreviewDummy);

        Picasso.with(this)
                .load(photo.getImageUrl())
                .placeholder(R.color.grey200)
                .into(imgPreview);

        if (savedInstanceState == null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                final int thumbnailTop = bundle.getInt(EXTRA_TOP);
                final int thumbnailLeft = bundle.getInt(EXTRA_LEFT);
                final int thumbnailWidth = bundle.getInt(EXTRA_WIDTH);
                final int thumbnailHeight = bundle.getInt(EXTRA_HEIGHT);
                originalOrientation = bundle.getInt(EXTRA_ORIENTATION);
                ViewTreeObserver observer = imgPreviewDummy.getViewTreeObserver();
                observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        imgPreviewDummy.getViewTreeObserver().removeOnPreDrawListener(this);

                        int[] screenLocation = new int[2];
                        imgPreviewDummy.getLocationOnScreen(screenLocation);
                        leftDelta = thumbnailLeft - screenLocation[0];
                        topDelta = thumbnailTop - screenLocation[1];
                        widthScale = (float) thumbnailWidth / (imgPreviewDummy.getWidth());
                        heightScale = (float) thumbnailHeight / imgPreviewDummy.getHeight();

                        startEnterAnimation();

                        return true;
                    }
                });
            }
        }
    }

    public void startEnterAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            imgPreviewDummy.setPivotX(0);
            imgPreviewDummy.setPivotY(0);
            imgPreviewDummy.setScaleX(widthScale);
            imgPreviewDummy.setScaleY(heightScale);
            imgPreviewDummy.setTranslationX(leftDelta);
            imgPreviewDummy.setTranslationY(topDelta);
        } else {
            AnimatorProxy proxy = AnimatorProxy.wrap(imgPreviewDummy);
            proxy.setPivotX(0);
            proxy.setPivotY(0);
            proxy.setScaleX(widthScale);
            proxy.setScaleY(heightScale);
            proxy.setTranslationX(leftDelta);
            proxy.setTranslationY(topDelta);
        }

        ViewPropertyAnimator.animate(imgPreviewDummy)
                .setDuration(ANIMATION_DURATION)
                .scaleX(1).scaleY(1)
                .translationX(0).translationY(0)
                .setInterpolator(INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imgPreview.setVisibility(View.VISIBLE);
                        imgPreviewDummy.setVisibility(View.GONE);
                        FabAnimationUtils.scaleIn(fab);
                    }
                });
    }

    public void startExitAnimation() {
        final boolean fadeOut;
        if (getResources().getConfiguration().orientation != originalOrientation) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                imgPreviewDummy.setPivotX(imgPreviewDummy.getWidth() / 2);
                imgPreviewDummy.setPivotY(imgPreviewDummy.getHeight() / 2);
            } else {
                AnimatorProxy proxy = AnimatorProxy.wrap(imgPreviewDummy);
                proxy.setPivotX(imgPreviewDummy.getWidth() / 2);
                proxy.setPivotY(imgPreviewDummy.getHeight() / 2);
            }
            leftDelta = 0;
            topDelta = 0;
            fadeOut = true;
        } else {
            fadeOut = false;
        }

        ViewPropertyAnimator.animate(imgPreviewDummy)
                .setDuration(ANIMATION_DURATION)
                .scaleX(widthScale).scaleY(heightScale)
                .translationX(leftDelta).translationY(topDelta)
                .setInterpolator(INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        finish();
                    }
                });

        if (fadeOut) {
            ViewPropertyAnimator.animate(imgPreviewDummy).alpha(0);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_scale_finish_enter, R.anim.activity_slide_finish_exit);
    }

}
