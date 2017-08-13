package com.konifar.materialcat.presentation.usecase.gallery

import com.konifar.materialcat.infra.repository.catphoto.CatImageRepository
import de.greenrobot.event.EventBus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class GetCatImageUseCaseImpl(
        val eventBus: EventBus,
        private val compositeDisposable: CompositeDisposable,
        private val catImageRepository: CatImageRepository
) : GetCatImagesUseCase {

    override fun requestGetPopular(text: String, page: Int, perPage: Int) {
        compositeDisposable.add(
                catImageRepository.findByTextOrderByPopular(text, page, perPage)
                        .subscribeBy(
                                onNext = { eventBus.postSticky(GetCatImagesUseCase.GetPopularCatImagesSuccessEvent(it)) },
                                onError = { eventBus.postSticky(GetCatImagesUseCase.GetPopularCatImagesFailureEvent(it)) }
                        )
        )
    }

    override fun requestGetNew(text: String, page: Int, perPage: Int) {
        compositeDisposable.add(
                catImageRepository.findByTextOrderByNew(text, page, perPage)
                        .subscribeBy(
                                onNext = { eventBus.postSticky(GetCatImagesUseCase.GetNewCatImagesSuccessEvent(it)) },
                                onError = { eventBus.postSticky(GetCatImagesUseCase.GetNewCatImagesFailureEvent(it)) }
                        )
        )
    }

    override fun destroy() {
        compositeDisposable.dispose()
    }

}
