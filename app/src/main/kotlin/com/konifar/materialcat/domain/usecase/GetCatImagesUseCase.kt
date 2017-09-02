package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImage
import io.reactivex.Observable

interface GetCatImagesUseCase {

    fun requestGetPopular(page: Int, shouldRefresh: Boolean): Observable<List<CatImage>>

    fun requestGetNew(page: Int, shouldRefresh: Boolean): Observable<List<CatImage>>

}
