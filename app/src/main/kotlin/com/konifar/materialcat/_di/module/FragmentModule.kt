package com.konifar.materialcat._di.module

import com.konifar.materialcat._di.ActivityScope
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule {

    @ActivityScope
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

}