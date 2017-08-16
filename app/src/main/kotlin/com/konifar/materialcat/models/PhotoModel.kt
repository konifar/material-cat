package com.konifar.materialcat.models

import android.content.Context
import com.konifar.materialcat.MainApplication
import com.konifar.materialcat.events.PhotoSearchCallbackEvent
import com.konifar.materialcat.infra.api.FlickrApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class PhotoModel private constructor(context: Context) {

    internal var flickerApiService: FlickrApiService

    init {
        flickerApiService = MainApplication.FLICKR_API
    }

    fun getCatPhotos(page: Int, sort: String) {
        flickerApiService.photosSearch(CAT_SEARCH_TEXT, page, PER_PAGE, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    EventBus.getDefault().post(PhotoSearchCallbackEvent(true, it.photos.photo, sort))
                }, {
                    EventBus.getDefault().post(PhotoSearchCallbackEvent(false, sort))
                })
    }

    companion object {

        private val CAT_SEARCH_TEXT = "cat"
        private val PER_PAGE = 36
        private var instance: PhotoModel? = null

        fun getInstance(context: Context): PhotoModel {
            if (instance == null) {
                instance = PhotoModel(context)
            }
            return instance as PhotoModel
        }
    }

}
