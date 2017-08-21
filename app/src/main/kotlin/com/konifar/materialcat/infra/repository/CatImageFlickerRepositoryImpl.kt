package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.domain.model.CatImage
import io.reactivex.Observable

class CatImageFlickerRepositoryImpl(
        private val api: CatImageRepository,
        private val inMemory: CatImageRepository
) : CatImageRepository {

    override fun findByTextOrderByPopular(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        return api.findByTextOrderByPopular(text, page, perPage)
    }

    override fun findByTextOrderByNew(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        return api.findByTextOrderByNew(text, page, perPage)
    }

}