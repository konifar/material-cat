package com.konifar.materialcat.presentation.photodetail

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.ActivityPhotoDetailBinding
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.extension.component
import com.konifar.materialcat.presentation.photodetail.model.PhotoDetailPresentationModel
import javax.inject.Inject

class PhotoDetailActivity : AppCompatActivity(), PhotoDetailContract.View {

    lateinit var binding: ActivityPhotoDetailBinding

    @Inject
    lateinit var presenter: PhotoDetailContract.Presenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)

        presenter.setUp(this)

        binding = DataBindingUtil.setContentView<ActivityPhotoDetailBinding>(this, R.layout.activity_photo_detail)

        initToolbar()

        presenter.requestGetCatImage(CatImageId(intent.extras.getString(CAT_IMAGE_ID)))
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

    override fun showCatImage(model: PhotoDetailPresentationModel) {
        binding.presentationModel = model
    }

    companion object {

        private val CAT_IMAGE_ID = "catImageId"

        fun createIntent(activity: Activity, catImageId: CatImageId): Intent
                = Intent(activity, PhotoDetailActivity::class.java).apply {
            putExtra(CAT_IMAGE_ID, catImageId.value)
        }
    }

}