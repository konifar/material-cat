package com.konifar.materialcat.extension

import android.support.v7.app.AppCompatActivity
import com.konifar.materialcat.presentation.di.ActivityComponent
import com.konifar.materialcat.presentation.di.ActivityModule

val AppCompatActivity.component: ActivityComponent
    get() = application.component.plus(ActivityModule(this))
