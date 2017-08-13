package com.konifar.materialcat.infra.di

import android.content.Context
import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.repository.catphoto.CatImageRepository
import com.konifar.materialcat.infra.repository.catphoto.FlickrCatImageRepositoryImpl
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule(private val context: Context) {

    @Provides
    fun provideCatImageRepository(flickrApiService: FlickrApiService): CatImageRepository = FlickrCatImageRepositoryImpl(flickrApiService)

}
