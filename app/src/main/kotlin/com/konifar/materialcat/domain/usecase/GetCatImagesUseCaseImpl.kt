package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.infra.data.SearchOrderType
import com.konifar.materialcat.infra.repository.CatImageFlickrRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GetCatImagesUseCaseImpl(private val catImageRepository: CatImageFlickrRepository) : GetCatImagesUseCase {

    companion object {
        private val SEARCH_TEXT = "cat"
        private val PER_PAGE = 36
    }

    override fun requestGetPopular(page: Int): Observable<List<CatImage>> {
        return catImageRepository.findByText(SearchOrderType.POPULAR, SEARCH_TEXT, page, PER_PAGE)
                .subscribeOn(Schedulers.io())
    }

    override fun requestGetNew(page: Int): Observable<List<CatImage>> {
        return catImageRepository.findByText(SearchOrderType.NEW, SEARCH_TEXT, page, PER_PAGE)
                .subscribeOn(Schedulers.io())
    }

}