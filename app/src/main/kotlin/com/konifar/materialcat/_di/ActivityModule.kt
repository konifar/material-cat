package com.konifar.materialcat._di

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityScope
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideActivity(): AppCompatActivity = activity

}