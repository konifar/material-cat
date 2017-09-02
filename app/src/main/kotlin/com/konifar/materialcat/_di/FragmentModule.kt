package com.konifar.materialcat._di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule {

    @ActivityScope
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

}