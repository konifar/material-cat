package com.konifar.materialcat

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.konifar.materialcat.infra.api.FlickrApiService
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.fabric.sdk.android.Fabric
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

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

        val FLICKR_API = Retrofit.Builder()
                .baseUrl("https://api.flickr.com")
                .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build())
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(FlickrApiService::class.java)

        operator fun get(context: Context): MainApplication {
            return context.applicationContext as MainApplication
        }
    }

}
