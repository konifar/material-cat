package com.konifar.materialcat.infra.repository.catimage.datasource

import com.konifar.materialcat.infra.data.FlickrPhotoJson
import io.reactivex.Observable

interface CatImageFlickrApiDataSource {

    fun findByText(sortType: String, text: String, page: Int, perPage: Int): Observable<List<FlickrPhotoJson>>

}