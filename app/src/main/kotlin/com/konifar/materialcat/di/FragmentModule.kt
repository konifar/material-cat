package com.konifar.materialcat.di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus

@Module
class FragmentModule {

    @ActivityScope
    @Provides
    fun provideEventBus() = EventBus()

    @ActivityScope
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

}