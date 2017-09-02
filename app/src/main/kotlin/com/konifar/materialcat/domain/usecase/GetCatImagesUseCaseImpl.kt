package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.infra.data.SearchOrderType
import com.konifar.materialcat.infra.repository.CatImageFlickrRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetCatImagesUseCaseImpl(private val catImageRepository: CatImageFlickrRepository) : GetCatImagesUseCase {

    companion object {
        private val SEARCH_TEXT = "cat"
        private val PER_PAGE = 36
    }

    override fun requestGetPopular(page: Int, shouldRefresh: Boolean): Observable<List<CatImage>> {
        return requestGet(page, shouldRefresh, SearchOrderType.POPULAR)
    }

    override fun requestGetNew(page: Int, shouldRefresh: Boolean): Observable<List<CatImage>> {
        return requestGet(page, shouldRefresh, SearchOrderType.NEW)
    }

    private fun requestGet(page: Int, shouldRefresh: Boolean, searchOrderType: SearchOrderType): Observable<List<CatImage>> {
        val single: Single<Int> = if (shouldRefresh) catImageRepository.clearCache(searchOrderType, SEARCH_TEXT) else Single.just(1)

        return single.toObservable()
                .flatMap { catImageRepository.findByText(searchOrderType, SEARCH_TEXT, page, PER_PAGE) }
                .subscribeOn(Schedulers.io())
    }

}