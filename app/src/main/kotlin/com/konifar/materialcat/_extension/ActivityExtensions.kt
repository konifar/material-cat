package com.konifar.materialcat._extension

import android.support.v7.app.AppCompatActivity
import com.konifar.materialcat.domain.di.UseCaseModule
import com.konifar.materialcat._di.module.ActivityModule
import com.konifar.materialcat._di.ActivityComponent

val AppCompatActivity.component: ActivityComponent
    get() = application.component.plus(ActivityModule(this), UseCaseModule())
