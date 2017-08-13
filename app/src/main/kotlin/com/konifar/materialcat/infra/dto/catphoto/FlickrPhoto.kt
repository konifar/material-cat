package com.konifar.materialcat.infra.dto.catphoto

import com.squareup.moshi.Json

data class FlickrPhoto(
        @Json(name = "id") val id: String,
        @Json(name = "owner") val owner: String,
        @Json(name = "secret") val secret: String,
        @Json(name = "server") val server: String,
        @Json(name = "farm") val farm: Int,
        @Json(name = "title") val title: String,
        @Json(name = "ispublic") val isPublic: Int,
        @Json(name = "isfriend") val isFriend: Int,
        @Json(name = "isfamily") val isFamily: Int
)
