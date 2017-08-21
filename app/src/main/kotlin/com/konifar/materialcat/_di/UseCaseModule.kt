package com.konifar.materialcat._di

import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCaseImpl
import com.konifar.materialcat.infra.repository.CatImageRepository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus


@Module
class UseCaseModule {

    @Provides
    fun provideGetCatImagesUseCase(
            eventBus: EventBus,
            compositeDisposable: CompositeDisposable,
            catImageRepository: CatImageRepository
    ): GetCatImagesUseCase = GetCatImagesUseCaseImpl(eventBus, compositeDisposable, catImageRepository)

}