package com.konifar.materialcat.infra.repository

import com.github.gfx.android.orma.annotation.OnConflict
import com.konifar.materialcat.infra.data.FlickrPhoto
import com.konifar.materialcat.infra.data.OrmaDatabase
import com.konifar.materialcat.infra.data.SearchOrderType
import io.reactivex.Observable

class CatImageFlickrDatabaseDataSource(
        private val orma: OrmaDatabase
) : CatImageFlickrDataSource {

    override fun findByText(searchOrderType: SearchOrderType, text: String, page: Int, perPage: Int): Observable<List<FlickrPhoto>> {
        return orma.selectFromFlickrPhoto()
                .typeEq(searchOrderType.toString())
                .limit(perPage.toLong())
                .offset(((page - 1) * perPage).toLong())
                .executeAsObservable()
                .toList()
                .toObservable()
    }

    override fun findById(id: String): Observable<FlickrPhoto> {
        return orma.selectFromFlickrPhoto()
                .idEq(id)
                .executeAsObservable()
    }

    override fun updateCache(searchOrderType: SearchOrderType, text: String, page: Int, photos: List<FlickrPhoto>) {
        orma.prepareInsertIntoFlickrPhoto(OnConflict.REPLACE)
                .executeAll(
                        photos.map {
                            it.apply {
                                type = searchOrderType.toString()
                                searchedText = text
                            }
                        }
                )
    }

    override fun clearCache(searchOrderType: SearchOrderType, text: String) {
        orma.deleteFromFlickrPhoto()
                .typeEq(searchOrderType.toString())
                .searchedTextEq(text)
                .execute()
    }

}