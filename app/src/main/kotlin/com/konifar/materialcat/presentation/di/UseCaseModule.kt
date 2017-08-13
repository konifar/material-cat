package com.konifar.materialcat.presentation.di

import com.konifar.materialcat.infra.repository.catphoto.CatImageRepository
import com.konifar.materialcat.presentation.usecase.gallery.GetCatImageUseCaseImpl
import com.konifar.materialcat.presentation.usecase.gallery.GetCatImagesUseCase
import dagger.Module
import dagger.Provides
import de.greenrobot.event.EventBus
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named


@Module
class UseCaseModule() {

    @Provides
    fun provideEventBus() = EventBus()

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideGetCatImagesUseCase(
            eventBus: EventBus,
            compositeDisposable: CompositeDisposable,
            @Named("flickrCatImageRepository") catImageRepository: CatImageRepository
    ): GetCatImagesUseCase = GetCatImageUseCaseImpl(eventBus, compositeDisposable, catImageRepository)

}
