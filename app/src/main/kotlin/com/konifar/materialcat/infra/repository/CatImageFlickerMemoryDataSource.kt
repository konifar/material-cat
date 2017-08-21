package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import io.reactivex.Observable

class CatImageFlickerMemoryDataSource(
        private val cacheForPopularPerPage: MutableMap<Int, List<CatImage>>,
        private val cacheForNewPerPage: MutableMap<Int, List<CatImage>>,
        private val cachePerId: MutableMap<CatImageId, CatImage>
) : CatImageRepository {

    override fun findByTextOrderByPopular(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        if (cacheForPopularPerPage[page] != null) {
            return Observable.just(cacheForPopularPerPage[page])
        }

        return Observable.empty()
    }

    override fun findByTextOrderByNew(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        if (cacheForNewPerPage[page] != null) {
            return Observable.just(cacheForNewPerPage[page])
        }

        return Observable.empty()
    }

    override fun findById(catImageId: CatImageId): Observable<CatImage> {
        if (cachePerId[catImageId] != null) {
            return Observable.just(cachePerId[catImageId])
        }
        return Observable.empty()
    }

}