package com.konifar.materialcat

import android.app.Application
import android.content.Context

import com.crashlytics.android.Crashlytics
import com.google.gson.GsonBuilder
import com.konifar.materialcat.infra.network.FlickrApiService

import io.fabric.sdk.android.Fabric
import retrofit.RestAdapter
import retrofit.converter.GsonConverter

class MainApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder().apply {
            applicationModule(ApplicationModule(this@MainApplication))
        }.build()
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }

    companion object {

        val FLICKR_API = RestAdapter.Builder()
                .setEndpoint(Constants.FLICKR_ENDPOINT)
                .setConverter(GsonConverter(GsonBuilder().setDateFormat(Constants.JSON_DATE_FORMAT).create()))
                .build()
                .create(FlickrApiService::class.java)

        operator fun get(context: Context): MainApplication {
            return context.applicationContext as MainApplication
        }
    }

}
