package com.konifar.materialcat.domain.gallery.usecase

import com.konifar.materialcat.domain.gallery.model.CatImage
import com.konifar.materialcat.presentation.FailureEvent
import com.konifar.materialcat.presentation.UseCase
import org.greenrobot.eventbus.EventBus

interface GetCatImagesUseCase : UseCase {

    fun eventBus(): EventBus

    fun requestGetPopular(page: Int, perPage: Int)

    fun requestGetNew(page: Int, perPage: Int)

    data class GetPopularCatImagesSuccessEvent(val catImages: List<CatImage>)

    data class GetPopularCatImagesFailureEvent(override val throwable: Throwable) : FailureEvent(throwable)

    data class GetNewCatImagesSuccessEvent(val catImages: List<CatImage>)

    data class GetNewCatImagesFailureEvent(override val throwable: Throwable) : FailureEvent(throwable)

}
