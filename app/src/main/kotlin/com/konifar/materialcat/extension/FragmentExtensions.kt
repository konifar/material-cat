package com.konifar.materialcat.extension

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.konifar.materialcat.di.FragmentComponent
import com.konifar.materialcat.di.FragmentModule
import com.konifar.materialcat.di.UseCaseModule

val Fragment.component: FragmentComponent
    get() = (activity as AppCompatActivity).application.component.plus(FragmentModule(), UseCaseModule())
