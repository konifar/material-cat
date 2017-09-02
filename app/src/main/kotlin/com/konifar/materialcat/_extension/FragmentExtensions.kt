package com.konifar.materialcat._extension

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.konifar.materialcat._di.FragmentComponent
import com.konifar.materialcat._di.FragmentModule
import com.konifar.materialcat._di.UseCaseModule

val Fragment.component: FragmentComponent
    get() = (activity as AppCompatActivity).application.component.plus(FragmentModule(), UseCaseModule())
