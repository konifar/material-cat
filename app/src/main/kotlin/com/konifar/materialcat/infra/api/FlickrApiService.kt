package com.konifar.materialcat.infra.api

import com.konifar.materialcat.BuildConfig
import com.konifar.materialcat.infra.dto.catphoto.FlickrPhotoList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    /**
     * https://www.flickr.com/services/api/flickr.photos.search.html
     */
    @GET("/services/rest?method=flickr.photos.search&api_key=" + BuildConfig.FLICKR_API_KEY + "&format=json&nojsoncallback=1")
    fun photosSearch(@Query("text") text: String,
                     @Query("page") page: Int?,
                     @Query("per_page") perpage: Int?,
                     @Query("sort") sort: String): Observable<FlickrPhotoList>

}