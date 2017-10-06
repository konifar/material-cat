package com.konifar.materialcat.di

import com.konifar.materialcat.di.module.FragmentModule
import com.konifar.materialcat.di.module.PresenterModule
import com.konifar.materialcat.di.scope.ActivityScope
import com.konifar.materialcat.domain.di.UseCaseModule
import com.konifar.materialcat.presentation.gallery.GalleryFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(
        FragmentModule::class,
        UseCaseModule::class,
        PresenterModule::class
))
interface FragmentComponent {

    fun inject(fragment: GalleryFragment)

}