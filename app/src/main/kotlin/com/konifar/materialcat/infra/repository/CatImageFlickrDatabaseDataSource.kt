package com.konifar.materialcat.infra.repository

import com.github.gfx.android.orma.annotation.OnConflict
import com.konifar.materialcat.infra.data.FlickrPhotoJson
import com.konifar.materialcat.infra.data.OrmaDatabase
import com.konifar.materialcat.infra.data.SearchOrderType
import io.reactivex.Observable
import io.reactivex.Single

class CatImageFlickrDatabaseDataSource(
        private val orma: OrmaDatabase
) : CatImageFlickrDataSource {

    override fun findByText(searchOrderType: SearchOrderType, text: String, page: Int, perPage: Int): Observable<List<FlickrPhotoJson>> {
        return orma.selectFromFlickrPhotoJson()
                .typeEq(searchOrderType.toString())
                .limit(perPage.toLong())
                .offset(((page - 1) * perPage).toLong())
                .executeAsObservable()
                .toList()
                .toObservable()
    }

    override fun findById(id: String): Observable<FlickrPhotoJson> {
        return orma.selectFromFlickrPhotoJson()
                .idEq(id)
                .executeAsObservable()
    }

    override fun updateCache(searchOrderType: SearchOrderType, text: String, page: Int, photoJsons: List<FlickrPhotoJson>) {
        orma.prepareInsertIntoFlickrPhotoJson(OnConflict.REPLACE)
                .executeAll(
                        photoJsons.map {
                            it.apply {
                                type = searchOrderType.toString()
                                searchedText = text
                            }
                        }
                )
    }

    override fun clearCache(searchOrderType: SearchOrderType, text: String): Single<Int> {
        return orma.deleteFromFlickrPhotoJson()
                .typeEq(searchOrderType.toString())
                .searchedTextEq(text)
                .executeAsSingle()
    }

}