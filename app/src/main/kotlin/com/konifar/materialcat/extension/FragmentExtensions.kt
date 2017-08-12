package com.konifar.materialcat.extension

import android.support.v4.app.Fragment
import com.konifar.materialcat.presentation.di.FragmentComponent
import com.konifar.materialcat.presentation.di.FragmentModule

val Fragment.component: FragmentComponent
    get() = activity.component.plus(FragmentModule(this))
