package com.konifar.materialcat.presentation.gallery

import com.konifar.materialcat.domain.gallery.model.CatImage

interface GalleryPageNavigator {

    fun openDetail(catImage: CatImage)

}
