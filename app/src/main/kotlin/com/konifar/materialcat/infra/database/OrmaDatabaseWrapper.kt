package com.konifar.materialcat.infra.database

import android.content.Context
import com.konifar.materialcat.infra.data.OrmaDatabase

class OrmaDatabaseWrapper(context: Context) {
    val orma: OrmaDatabase = OrmaDatabase.builder(context).build()
}