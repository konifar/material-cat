package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import io.reactivex.Observable

interface GetCatImageUseCase {

    fun requestGet(id: CatImageId): Observable<CatImage>

}