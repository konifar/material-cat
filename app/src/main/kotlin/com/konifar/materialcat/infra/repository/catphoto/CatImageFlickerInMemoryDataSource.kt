package com.konifar.materialcat.infra.repository.catphoto

import com.konifar.materialcat.domain.gallery.model.CatImage
import com.konifar.materialcat.domain.gallery.model.CatImageId
import io.reactivex.Observable

class CatImageFlickerInMemoryDataSource(
        private val cacheForPopular: MutableMap<CatImageId, CatImage>
) : CatImageRepository {

    override fun findByTextOrderByPopular(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        // TODO
        return Observable.just(cacheForPopular.values.toList())
    }

    override fun findByTextOrderByNew(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        // TODO
        return Observable.just(cacheForPopular.values.toList())
    }

    override fun findById(catImageId: CatImageId): Observable<CatImage> {
        if (cacheForPopular[catImageId] != null) {
            return Observable.just(cacheForPopular[catImageId])
        }
        return Observable.empty()
    }

}