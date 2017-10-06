package com.konifar.materialcat.infra.repository.catimage.datasource

import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.data.FlickrPhotoJson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class CatImageFlickrApiDataSourceImpl(private val flickrApiService: FlickrApiService) : CatImageFlickrApiDataSource {

    override fun findByText(sortType: String, text: String, page: Int, perPage: Int): Observable<List<FlickrPhotoJson>> {
        return flickrApiService.photosSearch(text, page, perPage, sortType)
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.photos.photo }
    }

}