package com.konifar.materialcat.infra.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides


@Module
class InfraModule(private val context: Context) {

    companion object {
        val SHARED_PREF_NAME: String = "preferences"
    }

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

}
