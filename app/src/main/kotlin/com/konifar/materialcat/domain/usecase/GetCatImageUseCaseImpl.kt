package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.repository.CatImageFlickrRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GetCatImageUseCaseImpl(private val catImageRepository: CatImageFlickrRepository) : GetCatImageUseCase {

    override fun requestGet(id: CatImageId): Observable<CatImage> {
        return catImageRepository.findById(id)
                .subscribeOn(Schedulers.io())
    }

}