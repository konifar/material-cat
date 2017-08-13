package com.konifar.materialcat.presentation.di

import com.konifar.materialcat.MainActivity
import com.konifar.materialcat.PhotoDetailActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: PhotoDetailActivity)

}
