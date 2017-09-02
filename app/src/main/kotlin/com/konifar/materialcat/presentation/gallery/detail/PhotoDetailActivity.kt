package com.konifar.materialcat.presentation.gallery.detail

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.konifar.materialcat.R
import com.konifar.materialcat._extension.component
import com.konifar.materialcat.databinding.ActivityPhotoDetailBinding
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.presentation.gallery.PhotoDetailPresenter
import javax.inject.Inject

class PhotoDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityPhotoDetailBinding

    @Inject
    lateinit var presenter: PhotoDetailPresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)

        presenter.viewModel = PhotoDetailViewModel()

        binding = DataBindingUtil.setContentView<ActivityPhotoDetailBinding>(this, R.layout.activity_photo_detail)
        binding.viewModel = presenter.viewModel

        initToolbar()

        presenter.requestGet(CatImageId(intent.extras.getString(CAT_IMAGE_ID)))
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        val bar = supportActionBar
        bar?.setDisplayHomeAsUpEnabled(true)
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

    companion object {

        private val CAT_IMAGE_ID = "catImageId"

        fun createIntent(activity: Activity, catImageId: CatImageId): Intent
                = Intent(activity, PhotoDetailActivity::class.java).apply {
            putExtra(CAT_IMAGE_ID, catImageId.value)
        }
    }

}