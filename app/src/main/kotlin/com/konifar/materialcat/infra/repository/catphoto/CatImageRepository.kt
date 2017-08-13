package com.konifar.materialcat.infra.repository.catphoto

import com.konifar.materialcat.domain.gallery.model.CatImage
import io.reactivex.Observable

interface CatImageRepository {

    fun findByTextOrderByPopular(text: String, page: Int, perPage: Int): Observable<List<CatImage>>

    fun findByTextOrderByNew(text: String, page: Int, perPage: Int): Observable<List<CatImage>>

}
