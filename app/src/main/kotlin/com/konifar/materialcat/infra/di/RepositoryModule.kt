package com.konifar.materialcat.infra.di

import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.repository.catphoto.CatImageRepository
import com.konifar.materialcat.infra.repository.catphoto.FlickrCatImageRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class RepositoryModule() {

    @Named("flickrCatImageRepository")
    @Singleton
    @Provides
    fun provideFlickrCatImageRepository(flickrApiService: FlickrApiService): CatImageRepository = FlickrCatImageRepositoryImpl(flickrApiService)


}
