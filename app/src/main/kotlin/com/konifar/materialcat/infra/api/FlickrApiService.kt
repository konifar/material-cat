package com.konifar.materialcat.infra.api

import com.konifar.materialcat.BuildConfig
import com.konifar.materialcat.Constants
import com.konifar.materialcat.infra.dto.catphoto.FlickrPhotoList
import com.konifar.materialcat.models.pojo.PhotoSource
import io.reactivex.Observable
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    /**
     * https://www.flickr.com/services/api/flickr.photos.search.html
     */
    @Deprecated(message = "User observable")
    @GET("/services/rest?method=flickr.photos.search&api_key=" + Constants.FLICKR_API_KEY + "&format=json&nojsoncallback=1")
    fun photosSearch(@Query("text") text: String,
                     @Query("page") page: Int?,
                     @Query("per_page") perpage: Int?,
                     @Query("sort") sort: String,
                     cb: Callback<PhotoSource>)

    @GET("/services/rest?method=flickr.photos.search&api_key=" + BuildConfig.FLICKR_API_KEY + "&format=json&nojsoncallback=1")
    fun photosSearch(@Query("text") text: String,
                     @Query("page") page: Int?,
                     @Query("per_page") perpage: Int?,
                     @Query("sort") sort: String): Observable<FlickrPhotoList>

    companion object {

        val SORT_INTERESTINGNESS_DESC = "interestingness-desc"
        val SORT_DATE_POSTED_DESC = "date-posted-desc"
    }

}
