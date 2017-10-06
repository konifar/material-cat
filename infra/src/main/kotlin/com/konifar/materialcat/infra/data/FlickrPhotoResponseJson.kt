package com.konifar.materialcat.infra.data

import com.squareup.moshi.Json

class FlickrPhotoResponseJson(
        @Json(name = "photos") val photos: FlickrPhotoMetaJson,
        @Json(name = "stat") val stat: String
)