package com.konifar.materialcat.domain.di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class DomainModule {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

}