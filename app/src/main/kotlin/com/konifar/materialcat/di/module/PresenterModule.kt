package com.konifar.materialcat.di.module

import com.konifar.materialcat.domain.usecase.GetCatImageUseCase
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase
import com.konifar.materialcat.presentation.gallery.GalleryContract
import com.konifar.materialcat.presentation.gallery.GalleryPresenter
import com.konifar.materialcat.presentation.photodetail.PhotoDetailContract
import com.konifar.materialcat.presentation.photodetail.PhotoDetailPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class PresenterModule {

    @Provides
    fun provideGalleryPresenter(getCatImagesUseCase: GetCatImagesUseCase, compositeDisposable: CompositeDisposable): GalleryContract.Presenter = GalleryPresenter(getCatImagesUseCase, compositeDisposable)

    @Provides
    fun providePhotoDetailPresenter(getCatImageUseCase: GetCatImageUseCase, compositeDisposable: CompositeDisposable): PhotoDetailContract.Presenter = PhotoDetailPresenter(getCatImageUseCase, compositeDisposable)

}