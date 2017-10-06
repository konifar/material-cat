package com.konifar.materialcat.di.module

import com.konifar.materialcat.di.scope.ActivityScope
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule {

    @ActivityScope
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

}