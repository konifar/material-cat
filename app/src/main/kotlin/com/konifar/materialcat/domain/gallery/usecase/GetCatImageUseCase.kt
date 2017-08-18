package com.konifar.materialcat.domain.gallery.usecase

import com.konifar.materialcat.domain.gallery.model.CatImage
import com.konifar.materialcat.domain.gallery.model.CatImageId
import com.konifar.materialcat.presentation.FailureEvent
import com.konifar.materialcat.presentation.UseCase

interface GetCatImageUseCase : UseCase {

    fun requestGet(id: CatImageId)

    data class GetCatImageSuccessEvent(val catImage: CatImage)

    data class GetCatImageFailureEvent(override val throwable: Throwable) : FailureEvent(throwable)

}