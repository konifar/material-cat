package com.konifar.materialcat._di

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.data.OrmaDatabase
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
    fun provideCatImageFlickerMemoryDataStore(): CatImageRepository = CatImageFlickerMemoryDataSource(HashMap<Int, List<CatImage>>(), HashMap<Int, List<CatImage>>(), HashMap<CatImageId, CatImage>())

    @Singleton
    @Provides
    @Named("flickr_database")
    fun provideCatImageFlickerDatabaseDataStore(orma: OrmaDatabase): CatImageRepository = CatImageFlickerDatabaseDataSource(orma)

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