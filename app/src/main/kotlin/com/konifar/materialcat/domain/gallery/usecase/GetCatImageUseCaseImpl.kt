package com.konifar.materialcat.domain.gallery.usecase

import com.konifar.materialcat.infra.repository.catphoto.CatImageRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class GetCatImageUseCaseImpl(
        val eventBus: EventBus,
        private val compositeDisposable: CompositeDisposable,
        private val catImageRepository: CatImageRepository
) : GetCatImagesUseCase {

    companion object {
        private val SEARCH_TEXT = "cat"
    }

    override fun eventBus(): EventBus {
        return eventBus
    }

    override fun requestGetPopular(page: Int, perPage: Int) {
        compositeDisposable.add(
                catImageRepository.findByTextOrderByPopular(SEARCH_TEXT, page, perPage)
                        .subscribeOn(Schedulers.io())
                        .subscribeBy(
                                onNext = { eventBus.postSticky(GetCatImagesUseCase.GetPopularCatImagesSuccessEvent(it)) },
                                onError = { eventBus.postSticky(GetCatImagesUseCase.GetPopularCatImagesFailureEvent(it)) }
                        )
        )
    }

    override fun requestGetNew(page: Int, perPage: Int) {
        compositeDisposable.add(
                catImageRepository.findByTextOrderByNew(SEARCH_TEXT, page, perPage)
                        .subscribeOn(Schedulers.io())
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
