package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.data.SearchOrderType
import io.reactivex.Observable
import io.reactivex.Single

interface CatImageFlickrRepository {

    fun findByText(searchOrderType: SearchOrderType, text: String, page: Int, perPage: Int): Observable<List<CatImage>>

    fun findById(catImageId: CatImageId): Observable<CatImage>

    fun clearCache(searchOrderType: SearchOrderType, text: String): Single<Int>

}