package com.konifar.materialcat._di

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.repository.CatImageFlickerApiDataSource
import com.konifar.materialcat.infra.repository.CatImageFlickerInMemoryDataSource
import com.konifar.materialcat.infra.repository.CatImageRepository
import com.konifar.materialcat.infra.repository.CatImageFlickerRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class RepositoryModule() {

    @Singleton
    @Provides
    @Named("flicker_api")
    fun provideCatImageFlickerApiDataStore(flickrApiService: FlickrApiService): CatImageRepository = CatImageFlickerApiDataSource(flickrApiService)

    @Singleton
    @Provides
    @Named("flicker_in_memory")
    fun provideCatImageFlickerInMemoryDataStore(): CatImageRepository = CatImageFlickerInMemoryDataSource(LinkedHashMap<CatImageId, CatImage>())

    @Singleton
    @Provides
    fun provideFlickrCatImageRepository(
            @Named("flicker_api") api: CatImageRepository,
            @Named("flicker_in_memory") inMemory: CatImageRepository
    ): CatImageRepository {
        return CatImageFlickerRepositoryImpl(api, inMemory)
    }

}