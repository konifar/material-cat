package com.konifar.materialcat.infra.dto.catphoto

import com.squareup.moshi.Json

data class FlickrPhotoList(
        @Json(name = "photos") val photos: Photos,
        @Json(name = "stat") val stat: String
) {

    class Photos {
        @Json(name = "page") var page: Int = 0
        @Json(name = "pages") var pages: Int = 0
        @Json(name = "perpage") var perpage: Int = 0
        @Json(name = "total") var total: String = ""
        @Json(name = "photo") var photo: List<FlickrPhoto> = listOf()
    }

}