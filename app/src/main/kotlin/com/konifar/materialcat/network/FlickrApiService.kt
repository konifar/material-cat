package com.konifar.materialcat.network

import com.konifar.materialcat.Constants
import com.konifar.materialcat.models.pojo.PhotoSource

import retrofit.Callback
import retrofit.http.GET
import retrofit.http.Query

interface FlickrApiService {

    /**
     * https://www.flickr.com/services/api/flickr.photos.search.html
     */
    @GET("/services/rest?method=flickr.photos.search&api_key=" + Constants.FLICKR_API_KEY + "&format=json&nojsoncallback=1")
    fun photosSearch(@Query("text") text: String,
                     @Query("page") page: Int?,
                     @Query("per_page") perpage: Int?,
                     @Query("sort") sort: String,
                     cb: Callback<PhotoSource>)

    companion object {

        val SORT_INTERESTINGNESS_DESC = "interestingness-desc"
        val SORT_DATE_POSTED_DESC = "date-posted-desc"
    }

}
