package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.infra.data.FlickrPhoto
import com.konifar.materialcat.infra.data.SearchOrderType
import io.reactivex.Observable
import io.reactivex.Single

interface CatImageFlickrDataSource {

    fun findByText(searchOrderType: SearchOrderType, text: String, page: Int, perPage: Int): Observable<List<FlickrPhoto>>

    fun findById(id: String): Observable<FlickrPhoto> = Observable.empty()

    fun updateCache(searchOrderType: SearchOrderType, text: String, page: Int, photos: List<FlickrPhoto>) = Any()

    fun clearCache(searchOrderType: SearchOrderType, text: String): Single<Int> = Single.never()

}