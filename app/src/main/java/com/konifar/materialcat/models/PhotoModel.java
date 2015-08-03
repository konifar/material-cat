package com.konifar.materialcat.models;

import android.content.Context;

import com.konifar.materialcat.MainApplication;
import com.konifar.materialcat.events.PhotoSearchCallbackEvent;
import com.konifar.materialcat.models.pojo.PhotoSource;
import com.konifar.materialcat.network.FlickrApiService;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PhotoModel {

    private static final String CAT_SEARCH_TEXT = "cat";
    private static final int PER_PAGE = 36;
    private static PhotoModel instance;

    FlickrApiService flickerApiService;

    private PhotoModel(Context context) {
        flickerApiService = MainApplication.FLICKR_API;
    }

    public static PhotoModel getInstance(Context context) {
        if (instance == null) {
            instance = new PhotoModel(context);
        }
        return instance;
    }

    public void getCatPhotos(int page) {
        flickerApiService.photosSearch(CAT_SEARCH_TEXT, page, PER_PAGE, new Callback<PhotoSource>() {
            @Override
            public void success(PhotoSource photoSource, Response response) {
                EventBus.getDefault().post(new PhotoSearchCallbackEvent(true, photoSource.getPhotos()));
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new PhotoSearchCallbackEvent(false));
            }
        });
    }

}
