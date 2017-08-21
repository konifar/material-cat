package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.presentation.FailureEvent
import com.konifar.materialcat.domain.UseCase

interface GetCatImageUseCase : UseCase {

    fun requestGet(id: CatImageId)

    data class GetCatImageSuccessEvent(val catImage: CatImage)

    data class GetCatImageFailureEvent(override val throwable: Throwable) : FailureEvent(throwable)

}