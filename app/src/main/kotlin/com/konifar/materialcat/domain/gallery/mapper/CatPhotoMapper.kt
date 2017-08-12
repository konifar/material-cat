package com.konifar.materialcat.domain.gallery.mapper

import com.konifar.materialcat.domain.gallery.model.CatImage
import com.konifar.materialcat.domain.gallery.model.CatImageId
import com.konifar.materialcat.models.pojo.Photo

object CatPhotoMapper {

    fun transform(photo: Photo): CatImage = CatImage(
            id = CatImageId(photo.id),
            imageUrl = photo.imageUrl,
            title = photo.title
    )

    fun transform(list: List<Photo>): MutableList<CatImage> = mutableListOf<CatImage>().apply {
        addAll(list.map { transform(it) })
    }

}
