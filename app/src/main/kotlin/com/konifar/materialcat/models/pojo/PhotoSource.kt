package com.konifar.materialcat.models.pojo

import com.google.gson.annotations.SerializedName

class PhotoSource : Model() {

    @SerializedName("photos")
    private val photos: Photos? = null
    @SerializedName("stat")
    private val stat: String? = null

    fun getPhotos(): List<Photo>? {
        return photos!!.photo
    }

    inner class Photos : Model() {
        @SerializedName("page")
        private val page: Int = 0
        @SerializedName("pages")
        private val pages: Int = 0
        @SerializedName("perpage")
        private val perpage: Int = 0
        @SerializedName("total")
        private val total: String? = null
        @SerializedName("photo")
        val photo: List<Photo>? = null
    }

}
