package com.konifar.materialcat.presentation.photodetail.model.converter

import com.konifar.materialcat.domain.model.CatImageDomainModel
import com.konifar.materialcat.presentation.photodetail.model.PhotoDetailPresentationModel

object PhotoDetailPresentationModelConverter {

    fun convert(domainModel: CatImageDomainModel): PhotoDetailPresentationModel = PhotoDetailPresentationModel(domainModel.title, domainModel.imageUrl)

}