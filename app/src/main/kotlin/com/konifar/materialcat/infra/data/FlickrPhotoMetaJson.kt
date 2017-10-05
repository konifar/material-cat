package com.konifar.materialcat.infra.data

import com.squareup.moshi.Json

class FlickrPhotoMetaJson(
        @Json(name = "page") val page: Int,
        @Json(name = "pages") val pages: Int,
        @Json(name = "perpage") val perpage: Int,
        @Json(name = "total") val total: String,
        @Json(name = "photo") val photo: List<FlickrPhotoJson>
)