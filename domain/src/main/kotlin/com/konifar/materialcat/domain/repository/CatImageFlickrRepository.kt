package com.konifar.materialcat.domain.repository

import com.konifar.materialcat.domain.model.CatImageDomainModel
import io.reactivex.Observable
import io.reactivex.Single

interface CatImageFlickrRepository {

    fun findByText(sortType: String, text: String, page: Int, perPage: Int): Observable<List<CatImageDomainModel>>

    fun findById(id: String): Observable<CatImageDomainModel>

    fun clearCache(id: String, text: String): Single<Int>

}