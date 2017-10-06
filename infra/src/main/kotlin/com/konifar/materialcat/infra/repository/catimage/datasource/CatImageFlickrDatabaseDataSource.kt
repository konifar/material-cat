package com.konifar.materialcat.infra.repository.catimage.datasource

import com.konifar.materialcat.infra.data.FlickrPhotoJson
import io.reactivex.Observable
import io.reactivex.Single

interface CatImageFlickrDatabaseDataSource {

    fun findByText(sortType: String, text: String, page: Int, perPage: Int): Observable<List<FlickrPhotoJson>>

    fun findById(id: String): Observable<FlickrPhotoJson>

    fun updateCache(sortType: String, text: String, page: Int, photoJsons: List<FlickrPhotoJson>)

    fun clearCache(id: String, text: String): Single<Int>

}