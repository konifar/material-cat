package com.konifar.materialcat.infra.di

import com.konifar.materialcat.domain.repository.CatImageFlickrRepository
import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.database.OrmaDatabaseWrapper
import com.konifar.materialcat.infra.repository.catimage.CatImageFlickrRepositoryImpl
import com.konifar.materialcat.infra.repository.catimage.datasource.CatImageFlickrApiDataSource
import com.konifar.materialcat.infra.repository.catimage.datasource.CatImageFlickrApiDataSourceImpl
import com.konifar.materialcat.infra.repository.catimage.datasource.CatImageFlickrDatabaseDataSource
import com.konifar.materialcat.infra.repository.catimage.datasource.CatImageFlickrDatabaseDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule() {

    @Singleton
    @Provides
    fun provideCatImageFlickerApiDataStore(flickrApiService: FlickrApiService): CatImageFlickrApiDataSource = CatImageFlickrApiDataSourceImpl(flickrApiService)

    @Singleton
    @Provides
    fun provideCatImageFlickerDatabaseDataStore(ormaDatabaseWrapper: OrmaDatabaseWrapper): CatImageFlickrDatabaseDataSource = CatImageFlickrDatabaseDataSourceImpl(ormaDatabaseWrapper.orma)

    @Singleton
    @Provides
    fun provideFlickrCatImageRepository(
            api: CatImageFlickrApiDataSource,
            database: CatImageFlickrDatabaseDataSource
    ): CatImageFlickrRepository = CatImageFlickrRepositoryImpl(api, database)

}