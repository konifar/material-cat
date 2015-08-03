package com.konifar.materialcat.network;

import com.konifar.materialcat.Constants;
import com.konifar.materialcat.models.pojo.PhotoSource;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FlickrApiService {

    /**
     * https://www.flickr.com/services/api/flickr.photos.search.html
     */
    @GET("/services/rest?method=flickr.photos.search&sort=interestingness-desc&api_key=" + Constants.FLICKR_API_KEY + "&format=json&nojsoncallback=1")
    void photosSearch(@Query("text") String text,
                      @Query("page") Integer page,
                      @Query("perpage") Integer perpage,
                      Callback<PhotoSource> cb);

}
