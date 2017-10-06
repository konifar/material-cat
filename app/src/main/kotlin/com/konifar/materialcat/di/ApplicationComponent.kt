package com.konifar.materialcat.di

import com.konifar.materialcat.MainApplication
import com.konifar.materialcat.di.module.ActivityModule
import com.konifar.materialcat.di.module.ApplicationModule
import com.konifar.materialcat.di.module.FragmentModule
import com.konifar.materialcat.domain.di.UseCaseModule
import com.konifar.materialcat.infra.di.InfraModule
import com.konifar.materialcat.infra.di.RepositoryModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        InfraModule::class,
        RepositoryModule::class
))
interface ApplicationComponent {

    fun inject(application: MainApplication)

    fun plus(activityModule: ActivityModule, useCaseModule: UseCaseModule): ActivityComponent

    fun plus(fragmentModule: FragmentModule, useCaseModule: UseCaseModule): FragmentComponent

}