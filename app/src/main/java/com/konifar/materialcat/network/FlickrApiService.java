package com.konifar.materialcat.network;

import com.konifar.materialcat.Constants;
import com.konifar.materialcat.models.pojo.PhotoSource;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface FlickrApiService {

    String SORT_INTERESTINGNESS_DESC = "interestingness-desc";
    String SORT_DATE_POSTED_DESC = "date-posted-desc";

    /**
     * https://www.flickr.com/services/api/flickr.photos.search.html
     */
    @GET("/services/rest?method=flickr.photos.search&api_key=" + Constants.FLICKR_API_KEY + "&format=json&nojsoncallback=1")
    void photosSearch(@Query("text") String text,
                      @Query("page") Integer page,
                      @Query("perpage") Integer perpage,
                      @Query("sort") String sort,
                      Callback<PhotoSource> cb);

}
