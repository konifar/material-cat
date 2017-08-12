package com.konifar.materialcat

import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import com.konifar.materialcat.databinding.ActivityPhotoDetailBinding
import com.konifar.materialcat.models.pojo.Photo
import com.konifar.materialcat.utils.FabAnimationUtils
import com.nineoldandroids.animation.Animator
import com.nineoldandroids.animation.AnimatorListenerAdapter
import com.nineoldandroids.view.ViewPropertyAnimator
import com.nineoldandroids.view.animation.AnimatorProxy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.codetail.animation.SupportAnimator
import io.codetail.animation.ViewAnimationUtils

class PhotoDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityPhotoDetailBinding

    private var leftDelta: Int = 0
    private var topDelta: Int = 0
    private var widthScale: Float = 0.toFloat()
    private var heightScale: Float = 0.toFloat()
    private var originalOrientation: Int = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPhotoDetailBinding>(this, R.layout.activity_photo_detail)

        val bundle = intent.extras
        val photo = bundle.getSerializable(Photo::class.java.simpleName) as Photo

        initToolbar(photo)
        bindPhotoInfo(photo)

        binding.fab.setOnClickListener { onClickFab() }

        Picasso.with(this)
                .load(photo.imageUrl)
                .placeholder(R.color.theme50)
                .into(binding.imgPreviewDummy)

        // For circular reveal animation
        Picasso.with(this)
                .load(photo.imageUrl)
                .placeholder(R.color.theme50)
                .into(binding.imgPreviewCover)

        initPallete(photo)

        if (savedInstanceState == null) {
            val thumbnailTop = bundle.getInt(EXTRA_TOP)
            val thumbnailLeft = bundle.getInt(EXTRA_LEFT)
            val thumbnailWidth = bundle.getInt(EXTRA_WIDTH)
            val thumbnailHeight = bundle.getInt(EXTRA_HEIGHT)
            originalOrientation = bundle.getInt(EXTRA_ORIENTATION)
            val observer = binding.imgPreviewDummy!!.viewTreeObserver
            observer.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // TODO setVisibility(View.gone) does not work...
                    // fab.setVisibility(View.GONE);
                    FabAnimationUtils.scaleOut(binding.fab, 50, null)

                    binding.imgPreviewDummy.viewTreeObserver.removeOnPreDrawListener(this)

                    val screenLocation = IntArray(2)
                    binding.imgPreviewDummy.getLocationOnScreen(screenLocation)
                    leftDelta = thumbnailLeft - screenLocation[0]
                    topDelta = thumbnailTop - screenLocation[1]
                    widthScale = thumbnailWidth.toFloat() / binding.imgPreviewDummy.width
                    heightScale = thumbnailHeight.toFloat() / binding.imgPreviewDummy.height

                    startEnterAnimation()

                    return true
                }
            })
        }

        onClickFab()

        initActivityTransitions()
    }

    private fun initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transition = Slide()
            transition.excludeTarget(android.R.id.statusBarBackground, true)
            transition.excludeTarget(R.id.img_preview_dummy, true)
            transition.interpolator = LinearOutSlowInInterpolator()
            transition.duration = 300
            window.enterTransition = transition
        }
    }

    private fun initPallete(photo: Photo) {
        Picasso.with(this)
                .load(photo.imageUrl)
                .placeholder(R.color.theme50)
                .into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                        binding.imgPreview.setImageBitmap(bitmap)
                        binding.colorPallete.initColors(bitmap)
                    }

                    override fun onBitmapFailed(errorDrawable: Drawable) {
                        //
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable) {
                        //
                    }
                })
    }

    private fun bindPhotoInfo(photo: Photo) {
        binding.photoInfoId.setTitleText(photo.id!!)
        binding.photoInfoOwner.setTitleText(photo.owner!!)
        binding.photoInfoSecret.setTitleText(photo.secret!!)
        binding.photoInfoServer.setTitleText(photo.server!!)
        binding.photoInfoTitle.setTitleText(photo.title!!)
        binding.photoInfoPublic.setTitleText(photo.isPublic().toString())
        binding.photoInfoFriend.setTitleText(photo.isFriend().toString())
        binding.photoInfoFamily.setTitleText(photo.isFamily().toString())
    }

    private fun initToolbar(photo: Photo) {
        setSupportActionBar(binding.toolbar)
        val bar = supportActionBar
        bar?.setDisplayHomeAsUpEnabled(true)

        binding.collapsingToolbar.setTitle(photo.title)
    }

    fun startEnterAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            binding.imgPreviewDummy.pivotX = 0f
            binding.imgPreviewDummy.pivotY = 0f
            binding.imgPreviewDummy.scaleX = widthScale
            binding.imgPreviewDummy.scaleY = heightScale
            binding.imgPreviewDummy.translationX = leftDelta.toFloat()
            binding.imgPreviewDummy.translationY = topDelta.toFloat()
        } else {
            val proxy = AnimatorProxy.wrap(binding.imgPreviewDummy)
            proxy.pivotX = 0f
            proxy.pivotY = 0f
            proxy.scaleX = widthScale
            proxy.scaleY = heightScale
            proxy.translationX = leftDelta.toFloat()
            proxy.translationY = topDelta.toFloat()
        }

        ViewPropertyAnimator.animate(binding.imgPreviewDummy)
                .setDuration(ANIMATION_DURATION)
                .scaleX(1f).scaleY(1f)
                .translationX(0f).translationY(0f)
                .setInterpolator(INTERPOLATOR)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        // AppBarLayout is set invisible, because AppbarLayout has default background color.
                        binding.appBar.visibility = View.VISIBLE
                        binding.imgPreview.visibility = View.VISIBLE
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            revealPreview()
                        }
                    }
                })
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun revealPreview() {
        val cx = (binding.imgPreview.left + binding.imgPreview.right) / 2
        val cy = (binding.imgPreview.top + binding.imgPreview.bottom) / 2

        val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val animator = ViewAnimationUtils.createCircularReveal(binding.imgPreview, cx, cy, 0f, finalRadius)
        animator.setInterpolator(INTERPOLATOR)
        animator.setDuration(300)
        animator.addListener(object : SupportAnimator.AnimatorListener {
            override fun onAnimationStart() {
                binding.imgPreviewDummy.visibility = View.GONE
                binding.imgPreviewCover.visibility = View.VISIBLE
                binding.imgPreview.visibility = View.VISIBLE
            }

            override fun onAnimationEnd() {
                FabAnimationUtils.scaleIn(binding.fab)
            }

            override fun onAnimationCancel() {
                //
            }

            override fun onAnimationRepeat() {
                //
            }
        })
        animator.start()
    }

    internal fun onClickFab() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var drawable = binding.fab.drawable
            if (drawable is AnimatedVectorDrawable) {
                if (drawable.isRunning) return

                val drawableResId = if (binding.fab.isSelected) R.drawable.ic_pause_to_play else R.drawable.ic_play_to_pause
                binding.fab.setImageResource(drawableResId)

                drawable = binding.fab.drawable
                (drawable as AnimatedVectorDrawable).start()
            }
        }

        if (binding.fab.isSelected) {
            binding.imgPreview.pause()
        } else {
            binding.imgPreview.resume()
        }

        binding.fab.isSelected = !binding.fab.isSelected
    }

    companion object {

        private val EXTRA_ORIENTATION = "extra_orientation"
        private val EXTRA_LEFT = "extra_left"
        private val EXTRA_TOP = "extra_top"
        private val EXTRA_WIDTH = "extra_width"
        private val EXTRA_HEIGHT = "extra_height"

        private val ANIMATION_DURATION: Long = 250
        private val INTERPOLATOR = FastOutSlowInInterpolator()

        fun start(activity: Activity, transitionView: View, photo: Photo) {
            val intent = Intent(activity, PhotoDetailActivity::class.java)
            intent.putExtra(Photo::class.java.simpleName, photo)

            val screenLocation = IntArray(2)
            transitionView.getLocationOnScreen(screenLocation)
            val orientation = activity.resources.configuration.orientation
            intent.putExtra(EXTRA_ORIENTATION, orientation)
            intent.putExtra(EXTRA_LEFT, screenLocation[0])
            intent.putExtra(EXTRA_TOP, screenLocation[1])
            intent.putExtra(EXTRA_WIDTH, transitionView.width)
            intent.putExtra(EXTRA_HEIGHT, transitionView.height)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(activity)
                activity.startActivity(intent, options.toBundle())
            } else {
                activity.startActivity(intent)
            }
            activity.overridePendingTransition(0, R.anim.activity_fade_out)
        }
    }

}
