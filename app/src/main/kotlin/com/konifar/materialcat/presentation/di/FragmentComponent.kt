package com.konifar.materialcat.presentation.di

import com.konifar.materialcat.views.fragments.CatsGridFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fragment: CatsGridFragment)

}
