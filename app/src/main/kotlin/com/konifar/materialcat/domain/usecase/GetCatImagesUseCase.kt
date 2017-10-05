package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImageDomainModel
import io.reactivex.Observable

interface GetCatImagesUseCase {

    fun requestGetPopular(page: Int, shouldRefresh: Boolean): Observable<List<CatImageDomainModel>>

    fun requestGetNew(page: Int, shouldRefresh: Boolean): Observable<List<CatImageDomainModel>>

}
