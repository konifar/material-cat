package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.model.CatImageDomainModel
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.domain.repository.CatImageFlickrRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GetCatImageUseCaseImpl(private val catImageRepository: CatImageFlickrRepository) : GetCatImageUseCase {

    override fun requestGet(id: CatImageId): Observable<CatImageDomainModel> {
        return catImageRepository.findById(id.value)
                .subscribeOn(Schedulers.io())
    }

}