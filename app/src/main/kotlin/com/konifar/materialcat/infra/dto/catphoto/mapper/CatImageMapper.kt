package com.konifar.materialcat.infra.dto.catphoto.mapper

import com.konifar.materialcat.domain.gallery.model.CatImage
import com.konifar.materialcat.domain.gallery.model.CatImageId
import com.konifar.materialcat.infra.dto.catphoto.FlickrPhoto
import com.konifar.materialcat.infra.dto.catphoto.FlickrPhotoList

object CatImageMapper {

    private fun transform(photo: FlickrPhoto): CatImage = CatImage(
            id = CatImageId(photo.id),
            imageUrl = "http://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
            title = photo.title
    )

    fun transform(list: FlickrPhotoList): MutableList<CatImage> = mutableListOf<CatImage>().apply {
        addAll(list.photos.map { transform(it) })
    }

}
