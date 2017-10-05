package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.infra.data.FlickrPhotoJson
import com.konifar.materialcat.infra.data.SearchOrderType
import io.reactivex.Observable
import io.reactivex.Single

interface CatImageFlickrDataSource {

    fun findByText(searchOrderType: SearchOrderType, text: String, page: Int, perPage: Int): Observable<List<FlickrPhotoJson>>

    fun findById(id: String): Observable<FlickrPhotoJson> = Observable.empty()

    fun updateCache(searchOrderType: SearchOrderType, text: String, page: Int, photoJsons: List<FlickrPhotoJson>) = Any()

    fun clearCache(searchOrderType: SearchOrderType, text: String): Single<Int> = Single.never()

}