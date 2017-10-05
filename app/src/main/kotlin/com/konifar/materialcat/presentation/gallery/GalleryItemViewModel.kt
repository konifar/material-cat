package com.konifar.materialcat.presentation.gallery

import android.databinding.BaseObservable
import com.konifar.materialcat.domain.model.CatImageDomainModel

class GalleryItemViewModel(catImageDomainModel: CatImageDomainModel) : BaseObservable() {

    val id = catImageDomainModel.id
    val imageUrl: String = catImageDomainModel.imageUrl

}