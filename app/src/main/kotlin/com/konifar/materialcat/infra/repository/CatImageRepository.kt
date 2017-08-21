package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import io.reactivex.Observable

interface CatImageRepository {

    fun findByTextOrderByPopular(text: String, page: Int, perPage: Int): Observable<List<CatImage>>

    fun findByTextOrderByNew(text: String, page: Int, perPage: Int): Observable<List<CatImage>>

    fun findById(catImageId: CatImageId): Observable<CatImage> = Observable.empty()

}