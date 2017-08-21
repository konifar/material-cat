package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.presentation.FailureEvent
import com.konifar.materialcat.domain.UseCase

interface GetCatImagesUseCase : UseCase {

    fun requestGetPopular(page: Int, perPage: Int)

    fun requestGetNew(page: Int, perPage: Int)

    data class GetPopularCatImagesSuccessEvent(val catImages: List<CatImage>, val page: Int)

    data class GetPopularCatImagesFailureEvent(override val throwable: Throwable, val page: Int) : FailureEvent(throwable)

    data class GetNewCatImagesSuccessEvent(val catImages: List<CatImage>, val page: Int)

    data class GetNewCatImagesFailureEvent(override val throwable: Throwable, val page: Int) : FailureEvent(throwable)

}
