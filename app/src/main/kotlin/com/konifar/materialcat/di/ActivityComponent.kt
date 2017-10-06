package com.konifar.materialcat.di

import com.konifar.materialcat.di.module.ActivityModule
import com.konifar.materialcat.di.scope.ActivityScope
import com.konifar.materialcat.domain.di.UseCaseModule
import com.konifar.materialcat.presentation.main.MainActivity
import com.konifar.materialcat.presentation.gallery.detail.PhotoDetailActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(
        ActivityModule::class,
        UseCaseModule::class
))
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: PhotoDetailActivity)

}