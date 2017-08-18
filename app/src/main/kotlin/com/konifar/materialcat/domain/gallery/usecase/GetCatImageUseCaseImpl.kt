package com.konifar.materialcat.domain.gallery.usecase

import com.konifar.materialcat.domain.gallery.model.CatImageId
import com.konifar.materialcat.infra.repository.catphoto.CatImageRepository
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus

class GetCatImageUseCaseImpl(
        val eventBus: EventBus,
        private val compositeDisposable: CompositeDisposable,
        private val catImageRepository: CatImageRepository
) : GetCatImageUseCase {

    override fun eventBus(): EventBus {
        return eventBus
    }

    override fun requestGet(id: CatImageId) {
//        compositeDisposable.add(
//                catImageRepository.findByTextOrderByPopular(SEARCH_TEXT, page, perPage)
//                        .subscribeOn(Schedulers.io())
//                        .subscribeBy(
//                                onNext = { eventBus.postSticky(GetCatImagesUseCase.GetPopularCatImagesSuccessEvent(it, page)) },
//                                onError = { eventBus.postSticky(GetCatImagesUseCase.GetPopularCatImagesFailureEvent(it, page)) }
//                        )
//        )
    }

    override fun destroy() {
        compositeDisposable.dispose()
    }

}
