package com.konifar.materialcat._di.module

import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.database.OrmaDatabaseWrapper
import com.konifar.materialcat.infra.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class RepositoryModule() {

    @Singleton
    @Provides
    @Named("flickr_api")
    fun provideCatImageFlickerApiDataStore(flickrApiService: FlickrApiService): CatImageFlickrDataSource = CatImageFlickrApiDataSource(flickrApiService)

    @Singleton
    @Provides
    @Named("flickr_database")
    fun provideCatImageFlickerDatabaseDataStore(ormaDatabaseWrapper: OrmaDatabaseWrapper): CatImageFlickrDataSource = CatImageFlickrDatabaseDataSource(ormaDatabaseWrapper.orma)

    @Singleton
    @Provides
    fun provideFlickrCatImageRepository(
            @Named("flickr_api") api: CatImageFlickrDataSource,
            @Named("flickr_database") database: CatImageFlickrDataSource
    ): CatImageFlickrRepository {
        return CatImageFlickrRepositoryImpl(api, database)
    }

}