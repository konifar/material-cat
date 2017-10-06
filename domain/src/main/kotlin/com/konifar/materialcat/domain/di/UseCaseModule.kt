package com.konifar.materialcat.domain.di

import com.konifar.materialcat.domain.repository.CatImageFlickrRepository
import com.konifar.materialcat.domain.usecase.GetCatImageUseCase
import com.konifar.materialcat.domain.usecase.GetCatImageUseCaseImpl
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCaseImpl
import dagger.Module
import dagger.Provides


@Module
class UseCaseModule {

    @Provides
    fun provideGetCatImagesUseCase(
            catImageRepository: CatImageFlickrRepository
    ): GetCatImagesUseCase = GetCatImagesUseCaseImpl(catImageRepository)

    @Provides
    fun provideGetCatImageUseCase(
            catImageRepository: CatImageFlickrRepository
    ): GetCatImageUseCase = GetCatImageUseCaseImpl(catImageRepository)

}