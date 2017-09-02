package com.konifar.materialcat._di

import com.konifar.materialcat.domain.usecase.GetCatImageUseCase
import com.konifar.materialcat.domain.usecase.GetCatImageUseCaseImpl
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCaseImpl
import com.konifar.materialcat.infra.repository.CatImageFlickrRepository
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