package com.konifar.materialcat.events

import com.konifar.materialcat.infra.dto.catphoto.FlickrPhoto

class PhotoSearchCallbackEvent {

    var isSuccess: Boolean = false
        private set
    var photos: List<FlickrPhoto>? = null
    var sort: String? = null
        private set

    constructor(success: Boolean, sort: String) {
        this.isSuccess = success
        this.sort = sort
    }

    constructor(success: Boolean, photos: List<FlickrPhoto>?, sort: String) {
        this.isSuccess = success
        this.photos = photos
        this.sort = sort
    }

}
