package com.konifar.materialcat.presentation.gallery.model

import android.databinding.BaseObservable
import com.konifar.materialcat.domain.model.CatImageDomainModel

class GalleryItemPresentationModel(catImageDomainModel: CatImageDomainModel) : BaseObservable() {

    val id = catImageDomainModel.id
    val imageUrl: String = catImageDomainModel.imageUrl

}