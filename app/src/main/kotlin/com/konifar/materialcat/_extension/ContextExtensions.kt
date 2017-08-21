package com.konifar.materialcat._extension

import android.content.Context
import com.konifar.materialcat._di.ApplicationComponent
import com.konifar.materialcat.MainApplication

val Context.application: MainApplication
    get() = applicationContext as MainApplication

val Context.component: ApplicationComponent
    get() = application.applicationComponent
