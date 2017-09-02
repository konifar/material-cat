package com.konifar.materialcat.infra.data

import com.squareup.moshi.Json

data class FlickrPhotoResponse(
        @Json(name = "photos") val photos: FlickrPhotoMeta,
        @Json(name = "stat") val stat: String
)