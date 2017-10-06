package com.konifar.materialcat.di.module

import android.support.v7.app.AppCompatActivity
import com.konifar.materialcat.di.scope.ActivityScope
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