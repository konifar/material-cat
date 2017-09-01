package com.konifar.materialcat._di

import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.data.FlickrPhoto
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
    fun provideCatImageFlickerApiDataStore(flickrApiService: FlickrApiService): CatImageRepository = CatImageFlickerApiDataSource(flickrApiService)

    @Singleton
    @Provides
    @Named("flickr_memory")
    fun provideCatImageFlickerMemoryDataStore(): CatImageRepository = CatImageFlickerMemoryDataSource(HashMap<Int, List<FlickrPhoto>>(), HashMap<Int, List<FlickrPhoto>>(), HashMap<String, FlickrPhoto>())

    @Singleton
    @Provides
    @Named("flickr_database")
    fun provideCatImageFlickerDatabaseDataStore(ormaDatabaseWrapper: OrmaDatabaseWrapper): CatImageRepository = CatImageFlickerDatabaseDataSource(ormaDatabaseWrapper.orma)

    @Singleton
    @Provides
    fun provideFlickrCatImageRepository(
            @Named("flickr_api") api: CatImageRepository,
            @Named("flickr_database") database: CatImageRepository,
            @Named("flickr_memory") memory: CatImageRepository
    ): CatImageRepository {
        return CatImageFlickerRepositoryImpl(api, database, memory)
    }

}