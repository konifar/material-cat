package com.konifar.materialcat.extension

import android.support.v7.app.AppCompatActivity
import com.konifar.materialcat.domain.di.UseCaseModule
import com.konifar.materialcat.di.module.ActivityModule
import com.konifar.materialcat.di.ActivityComponent

val AppCompatActivity.component: ActivityComponent
    get() = application.component.plus(ActivityModule(this), UseCaseModule())
