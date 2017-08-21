package com.konifar.materialcat.presentation

import retrofit2.HttpException

abstract class FailureEvent(open val throwable: Throwable) {

    fun isHttpConnectionError(throwable: Throwable): Boolean {
        return throwable is HttpException && throwable.response() != null
    }

}
