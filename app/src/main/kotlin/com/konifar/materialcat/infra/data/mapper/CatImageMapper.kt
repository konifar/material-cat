package com.konifar.materialcat.infra.data.mapper

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.data.FlickrPhoto
import com.konifar.materialcat.infra.data.FlickrPhotoList

object CatImageMapper {

    private fun transform(photo: FlickrPhoto): CatImage = CatImage(
            id = CatImageId(photo.id),
            imageUrl = "http://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
            title = photo.title
    )

    fun transform(list: FlickrPhotoList): MutableList<CatImage> = mutableListOf<CatImage>().apply {
        addAll(list.photos.photo.map { transform(it) })
    }

}