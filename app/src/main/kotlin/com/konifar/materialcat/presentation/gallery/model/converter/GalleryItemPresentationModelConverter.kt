package com.konifar.materialcat.presentation.gallery.model.converter

import com.konifar.materialcat.domain.model.CatImageDomainModel
import com.konifar.materialcat.presentation.gallery.model.GalleryItemPresentationModel

object GalleryItemPresentationModelConverter {

    fun convert(domainModel: CatImageDomainModel): GalleryItemPresentationModel = GalleryItemPresentationModel(domainModel.id, domainModel.imageUrl)

    fun convert(domainModels: List<CatImageDomainModel>): List<GalleryItemPresentationModel> = domainModels.map { convert(it) }

}