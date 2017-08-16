package com.konifar.materialcat.presentation.di

import com.konifar.materialcat.presentation.gallery.GalleryFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fragment: GalleryFragment)

}
