package com.konifar.materialcat.extension

import android.support.v7.app.AppCompatActivity
import com.konifar.materialcat.di.UseCaseModule
import com.konifar.materialcat.di.ActivityModule
import com.konifar.materialcat.di.ActivityComponent

val AppCompatActivity.component: ActivityComponent
    get() = application.component.plus(ActivityModule(this), UseCaseModule())
