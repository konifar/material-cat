package com.konifar.materialcat.infra.repository.catimage.datasource

import com.github.gfx.android.orma.annotation.OnConflict
import com.konifar.materialcat.infra.data.FlickrPhotoJson
import com.konifar.materialcat.infra.data.OrmaDatabase
import io.reactivex.Observable
import io.reactivex.Single

class CatImageFlickrDatabaseDataSourceImpl(
        private val orma: OrmaDatabase
) : CatImageFlickrDatabaseDataSource {

    override fun findByText(sortType: String, text: String, page: Int, perPage: Int): Observable<List<FlickrPhotoJson>> {
        return orma.selectFromFlickrPhotoJson()
                .typeEq(sortType)
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

    override fun updateCache(sortType: String, text: String, page: Int, photoJsons: List<FlickrPhotoJson>) {
        orma.prepareInsertIntoFlickrPhotoJson(OnConflict.REPLACE)
                .executeAll(
                        photoJsons.map {
                            it.apply {
                                type = sortType
                                searchedText = text
                            }
                        }
                )
    }

    override fun clearCache(id: String, text: String): Single<Int> {
        return orma.deleteFromFlickrPhotoJson()
                .typeEq(id)
                .searchedTextEq(text)
                .executeAsSingle()
    }

}