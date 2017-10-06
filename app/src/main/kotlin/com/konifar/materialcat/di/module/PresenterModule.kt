package com.konifar.materialcat.di.module

import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase
import com.konifar.materialcat.presentation.gallery.GalleryContract
import com.konifar.materialcat.presentation.gallery.GalleryPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class PresenterModule {

    @Provides
    fun provideGalleryPresenter(getCatImagesUseCase: GetCatImagesUseCase, compositeDisposable: CompositeDisposable): GalleryContract.Presenter = GalleryPresenter(getCatImagesUseCase, compositeDisposable)

}