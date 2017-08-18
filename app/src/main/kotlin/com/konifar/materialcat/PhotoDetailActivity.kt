package com.konifar.materialcat

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.view.MenuItem
import com.konifar.materialcat.databinding.ActivityPhotoDetailBinding
import com.konifar.materialcat.domain.gallery.model.CatImageId
import com.konifar.materialcat.extension.component
import com.konifar.materialcat.infra.dto.catphoto.FlickrPhoto
import com.konifar.materialcat.presentation.gallery.GalleryPresenter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class PhotoDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityPhotoDetailBinding

    lateinit var presenter: GalleryPresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityPhotoDetailBinding>(this, R.layout.activity_photo_detail)

        val bundle = intent.extras
        val catImageId = CatImageId(bundle.getString(CAT_IMAGE_ID))

//        initToolbar(photo)
//        bindPhotoInfo(photo)

        binding.fab.setOnClickListener { onClickFab() }

//        Picasso.with(this)
//                .load("http://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg")
//                .placeholder(R.color.theme50)
//                .into(binding.imgPreviewDummy)

        // For circular reveal animation
//        Picasso.with(this)
//                .load("http://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg")
//                .placeholder(R.color.theme50)
//                .into(binding.imgPreviewCover)

//        initPallete(photo)

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

    private fun initPallete(photo: FlickrPhoto) {
        Picasso.with(this)
                .load("http://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg")
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

    private fun bindPhotoInfo(photo: FlickrPhoto) {
        binding.photoInfoId.setTitleText(photo.id)
        binding.photoInfoOwner.setTitleText(photo.owner)
        binding.photoInfoSecret.setTitleText(photo.secret)
        binding.photoInfoServer.setTitleText(photo.server)
        binding.photoInfoTitle.setTitleText(photo.title)
    }

    private fun initToolbar(photo: FlickrPhoto) {
        setSupportActionBar(binding.toolbar)
        val bar = supportActionBar
        bar?.setDisplayHomeAsUpEnabled(true)

        binding.collapsingToolbar.setTitle(photo.title)
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

        private val CAT_IMAGE_ID = "catImageId"

        fun createIntent(activity: Activity, catImageId: CatImageId): Intent
                = Intent(activity, PhotoDetailActivity::class.java).apply {
            putExtra(CAT_IMAGE_ID, catImageId.value)
        }
    }

}