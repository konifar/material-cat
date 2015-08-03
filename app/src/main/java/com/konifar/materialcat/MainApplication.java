package com.konifar.materialcat;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.gson.GsonBuilder;
import com.konifar.materialcat.network.FlickrApiService;

import io.fabric.sdk.android.Fabric;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class MainApplication extends Application {

    public static final FlickrApiService FLICKR_API = new RestAdapter.Builder()
            .setEndpoint(Constants.FLICKR_ENDPOINT)
            .setConverter(new GsonConverter(new GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create()))
            .build()
            .create(FlickrApiService.class);

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

}
