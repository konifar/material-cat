package com.konifar.materialcat._extension

import android.support.v7.app.AppCompatActivity
import com.konifar.materialcat._di.UseCaseModule
import com.konifar.materialcat._di.ActivityModule
import com.konifar.materialcat._di.ActivityComponent

val AppCompatActivity.component: ActivityComponent
    get() = application.component.plus(ActivityModule(this), UseCaseModule())
