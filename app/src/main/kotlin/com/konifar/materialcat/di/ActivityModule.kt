package com.konifar.materialcat.di

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityScope
    @Provides
    fun provideEventBus() = EventBus()

    @ActivityScope
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideActivity(): AppCompatActivity = activity

}