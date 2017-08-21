package com.konifar.materialcat._di

import com.konifar.materialcat.presentation.main.MainActivity
import com.konifar.materialcat.presentation.gallery.PhotoDetailActivity
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