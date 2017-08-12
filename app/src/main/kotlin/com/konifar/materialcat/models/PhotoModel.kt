package com.konifar.materialcat.models

import android.content.Context

import com.konifar.materialcat.MainApplication
import com.konifar.materialcat.events.PhotoSearchCallbackEvent
import com.konifar.materialcat.models.pojo.PhotoSource
import com.konifar.materialcat.network.FlickrApiService

import de.greenrobot.event.EventBus
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response

class PhotoModel private constructor(context: Context) {

    internal var flickerApiService: FlickrApiService

    init {
        flickerApiService = MainApplication.FLICKR_API
    }

    fun getCatPhotos(page: Int, sort: String) {
        flickerApiService.photosSearch(CAT_SEARCH_TEXT, page, PER_PAGE, sort,
                object : Callback<PhotoSource> {
                    override fun success(photoSource: PhotoSource, response: Response) {
                        EventBus.getDefault().post(PhotoSearchCallbackEvent(true, photoSource.getPhotos(), sort))
                    }

                    override fun failure(error: RetrofitError) {
                        EventBus.getDefault().post(PhotoSearchCallbackEvent(false, sort))
                    }
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
