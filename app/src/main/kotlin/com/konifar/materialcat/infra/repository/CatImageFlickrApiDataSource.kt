package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.data.FlickrPhoto
import com.konifar.materialcat.infra.data.SearchOrderType
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class CatImageFlickrApiDataSource(private val flickrApiService: FlickrApiService) : CatImageFlickrDataSource {

    override fun findByText(searchOrderType: SearchOrderType, text: String, page: Int, perPage: Int): Observable<List<FlickrPhoto>> {
        return flickrApiService.photosSearch(text, page, perPage, searchOrderType.flickrSortString)
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.photos.photo }
    }

}