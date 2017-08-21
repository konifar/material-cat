package com.konifar.materialcat._extension

import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.konifar.materialcat.R

object FabAnimationUtils {

    private val DEFAULT_DURATION = 200L
    private val FAST_OUT_SLOW_IN_INTERPOLATOR = FastOutSlowInInterpolator()

    @JvmOverloads fun scaleIn(fab: View, duration: Long = DEFAULT_DURATION, callback: ScaleCallback? = null) {
        fab.visibility = View.VISIBLE
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewCompat.animate(fab)
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .alpha(1.0f)
                    .setDuration(duration)
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .withLayer()
                    .setListener(object : ViewPropertyAnimatorListener {
                        override fun onAnimationStart(view: View) {
                            callback?.onAnimationStart()
                        }

                        override fun onAnimationCancel(view: View) {}

                        override fun onAnimationEnd(view: View) {
                            view.visibility = View.VISIBLE
                            callback?.onAnimationEnd()
                        }
                    }).start()
        } else {
            val anim = AnimationUtils.loadAnimation(fab.context, R.anim.design_fab_in)
            anim.duration = duration
            anim.interpolator = FAST_OUT_SLOW_IN_INTERPOLATOR
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    callback?.onAnimationStart()
                }

                override fun onAnimationEnd(animation: Animation) {
                    fab.visibility = View.VISIBLE
                    callback?.onAnimationEnd()
                }

                override fun onAnimationRepeat(animation: Animation) {
                    //
                }
            })
            fab.startAnimation(anim)
        }
    }

    fun scaleOut(fab: View, callback: ScaleCallback) {
        scaleOut(fab, DEFAULT_DURATION, callback)
    }

    @JvmOverloads fun scaleOut(fab: View, duration: Long = DEFAULT_DURATION, callback: ScaleCallback? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewCompat.animate(fab)
                    .scaleX(0.0f)
                    .scaleY(0.0f).alpha(0.0f)
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setDuration(duration)
                    .withLayer()
                    .setListener(object : ViewPropertyAnimatorListener {
                        override fun onAnimationStart(view: View) {
                            callback?.onAnimationStart()
                        }

                        override fun onAnimationCancel(view: View) {}

                        override fun onAnimationEnd(view: View) {
                            view.visibility = View.INVISIBLE
                            callback?.onAnimationEnd()
                        }
                    }).start()
        } else {
            val anim = AnimationUtils.loadAnimation(fab.context, R.anim.design_fab_out)
            anim.interpolator = FAST_OUT_SLOW_IN_INTERPOLATOR
            anim.duration = duration
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    callback?.onAnimationStart()
                }

                override fun onAnimationEnd(animation: Animation) {
                    fab.visibility = View.INVISIBLE
                    callback?.onAnimationEnd()
                }

                override fun onAnimationRepeat(animation: Animation) {
                    //
                }
            })
            fab.startAnimation(anim)
        }
    }

    interface ScaleCallback {
        fun onAnimationStart()

        fun onAnimationEnd()
    }


}
