package com.konifar.materialcat

import com.konifar.materialcat.domain.di.DomainModule
import com.konifar.materialcat.infra.di.InfraModule
import com.konifar.materialcat.presentation.di.ActivityComponent
import com.konifar.materialcat.presentation.di.ActivityModule
import com.konifar.materialcat.presentation.di.FragmentComponent
import com.konifar.materialcat.presentation.di.FragmentModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ApplicationModule::class, InfraModule::class, DomainModule::class))
interface ApplicationComponent {

    fun inject(application: MainApplication)

    fun plus(module: ActivityModule): ActivityComponent

    fun plus(module: FragmentModule): FragmentComponent
}
