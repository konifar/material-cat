package com.konifar.materialcat.presentation.gallery

import com.konifar.materialcat.domain.model.CatImageId

interface GalleryPageNavigator {

    fun openDetail(catImageId: CatImageId)

}
