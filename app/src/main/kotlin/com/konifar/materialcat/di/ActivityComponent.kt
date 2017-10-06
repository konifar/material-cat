package com.konifar.materialcat.di

import com.konifar.materialcat.di.module.ActivityModule
import com.konifar.materialcat.di.module.PresenterModule
import com.konifar.materialcat.di.scope.ActivityScope
import com.konifar.materialcat.domain.di.UseCaseModule
import com.konifar.materialcat.presentation.photodetail.PhotoDetailActivity
import com.konifar.materialcat.presentation.main.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(
        ActivityModule::class,
        PresenterModule::class,
        UseCaseModule::class
))
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: PhotoDetailActivity)

}