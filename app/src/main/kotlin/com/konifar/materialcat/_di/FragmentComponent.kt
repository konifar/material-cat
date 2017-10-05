package com.konifar.materialcat._di

import com.konifar.materialcat._di.module.FragmentModule
import com.konifar.materialcat.domain.di.UseCaseModule
import com.konifar.materialcat.presentation.gallery.GalleryFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(
        FragmentModule::class,
        UseCaseModule::class
))
interface FragmentComponent {

    fun inject(fragment: GalleryFragment)

}