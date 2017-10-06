package com.konifar.materialcat.infra.data.mapper

import com.konifar.materialcat.domain.model.CatImageDomainModel
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.data.FlickrPhotoJson

object CatImageDomainModelConverter {

    fun convert(photoJson: FlickrPhotoJson): CatImageDomainModel = CatImageDomainModel(
            id = CatImageId(photoJson.id),
            imageUrl = "http://farm${photoJson.farm}.staticflickr.com/${photoJson.server}/${photoJson.id}_${photoJson.secret}.jpg",
            title = photoJson.title
    )

    fun convert(list: List<FlickrPhotoJson>): MutableList<CatImageDomainModel> = mutableListOf<CatImageDomainModel>().apply {
        addAll(list.map { convert(it) })
    }

}