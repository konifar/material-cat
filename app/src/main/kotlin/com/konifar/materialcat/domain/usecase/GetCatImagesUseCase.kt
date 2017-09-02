package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImage
import io.reactivex.Observable

interface GetCatImagesUseCase {

    fun requestGetPopular(page: Int): Observable<List<CatImage>>

    fun requestGetNew(page: Int): Observable<List<CatImage>>

}
