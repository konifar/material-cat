package com.konifar.materialcat.infra.dto.catphoto

import com.squareup.moshi.Json

data class FlickrPhotoList(
        @Json(name = "photos") val photos: List<FlickrPhoto>,
        @Json(name = "stat") val stat: String
)
