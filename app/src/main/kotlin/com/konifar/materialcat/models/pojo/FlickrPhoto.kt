package com.konifar.materialcat.models.pojo

import com.google.gson.annotations.SerializedName

class FlickrPhoto : Model() {

    @SerializedName("id")
    lateinit var id: String
    @SerializedName("owner")
    lateinit var owner: String
    @SerializedName("secret")
    lateinit var secret: String
    @SerializedName("server")
    lateinit var server: String
    @SerializedName("farm")
    val farm: Int = 0
    @SerializedName("title")
    lateinit var title: String
    @SerializedName("ispublic")
    private val isPublic: Int = 0
    @SerializedName("isfriend")
    private val isFriend: Int = 0
    @SerializedName("isfamily")
    private val isFamily: Int = 0

    val imageUrl: String
        get() {
            val sb = StringBuilder()
            sb.append("http://farm")
            sb.append(farm)
            sb.append(".staticflickr.com/")
            sb.append(server)
            sb.append("/")
            sb.append(id)
            sb.append("_")
            sb.append(secret)
            sb.append(".jpg")

            return sb.toString()
        }

    fun isPublic(): Boolean {
        return isPublic != 0
    }

    fun isFriend(): Boolean {
        return isFriend != 0
    }

    fun isFamily(): Boolean {
        return isFamily != 0
    }

}
