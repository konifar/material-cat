package com.konifar.materialcat.presentation.gallery

import android.databinding.BaseObservable
import com.konifar.materialcat.domain.gallery.model.CatImage

class GalleryItemViewModel(
        catImage: CatImage
) : BaseObservable() {

    val imageUrl: String = catImage.imageUrl

}
