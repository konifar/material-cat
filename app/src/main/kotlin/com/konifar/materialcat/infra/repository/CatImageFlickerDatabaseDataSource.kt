package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.data.OrmaDatabase
import io.reactivex.Observable

class CatImageFlickerDatabaseDataSource(
        private val orma: OrmaDatabase
) : CatImageRepository {

    override fun findByTextOrderByPopular(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        return Observable.empty()
    }

    override fun findByTextOrderByNew(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        return Observable.empty()
    }

    override fun findById(catImageId: CatImageId): Observable<CatImage> {
        return Observable.empty()
    }

}