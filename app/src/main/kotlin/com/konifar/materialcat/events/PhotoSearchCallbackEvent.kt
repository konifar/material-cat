package com.konifar.materialcat.events

import com.konifar.materialcat.models.pojo.Photo

class PhotoSearchCallbackEvent {

    var isSuccess: Boolean = false
        private set
    var photos: List<Photo>? = null
    var sort: String? = null
        private set

    constructor(success: Boolean, sort: String) {
        this.isSuccess = success
        this.sort = sort
    }

    constructor(success: Boolean, photos: List<Photo>?, sort: String) {
        this.isSuccess = success
        this.photos = photos
        this.sort = sort
    }

}
