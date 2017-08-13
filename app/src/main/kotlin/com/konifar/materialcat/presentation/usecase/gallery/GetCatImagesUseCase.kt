package com.konifar.materialcat.presentation.usecase.gallery

import com.konifar.materialcat.domain.gallery.model.CatImage
import com.konifar.materialcat.presentation.FailureEvent
import com.konifar.materialcat.presentation.UseCase

interface GetCatImagesUseCase: UseCase {

    fun requestGetPopular(text: String, page: Int, perPage: Int)

    fun requestGetNew(text: String, page: Int, perPage: Int)

    data class GetPopularCatImagesSuccessEvent(val catImages: List<CatImage>)

    data class GetPopularCatImagesFailureEvent(override val throwable: Throwable) : FailureEvent(throwable)

    data class GetNewCatImagesSuccessEvent(val catImages: List<CatImage>)

    data class GetNewCatImagesFailureEvent(override val throwable: Throwable) : FailureEvent(throwable)

}
