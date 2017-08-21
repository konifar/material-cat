package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.data.OrmaDatabase
import com.konifar.materialcat.infra.data.mapper.CatImageMapper
import io.reactivex.Observable

class CatImageFlickerDatabaseDataSource(
        private val orma: OrmaDatabase
) : CatImageRepository {

    override fun findByTextOrderByPopular(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        return findByOrder(text, page, perPage, "popular")
    }

    override fun findByTextOrderByNew(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        return findByOrder(text, page, perPage, "new")
    }

    override fun findById(catImageId: CatImageId): Observable<CatImage> {
        return orma.selectFromFlickrPhoto()
                .idEq(catImageId.value)
                .executeAsObservable()
                .map { CatImageMapper.transform(it) }
    }

    private fun findByOrder(text: String, page: Int, perPage: Int, type: String): Observable<List<CatImage>> {
        return orma.selectFromFlickrPhoto()
                .typeEq(type)
                .limit(perPage.toLong())
                .offset(((page - 1) * perPage).toLong())
                .executeAsObservable()
                .toList()
                .toObservable()
                .map { CatImageMapper.transform(it) }
    }

}