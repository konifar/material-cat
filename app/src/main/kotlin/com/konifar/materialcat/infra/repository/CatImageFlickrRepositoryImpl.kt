package com.konifar.materialcat.infra.repository

import com.konifar.materialcat.domain.model.CatImageDomainModel
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.data.SearchOrderType
import com.konifar.materialcat.infra.data.mapper.CatImageMapper
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CatImageFlickrRepositoryImpl(
        private val api: CatImageFlickrDataSource,
        private val database: CatImageFlickrDataSource
) : CatImageFlickrRepository {

    override fun findByText(searchOrderType: SearchOrderType, text: String, page: Int, perPage: Int): Observable<List<CatImageDomainModel>> {
        return Observable.concat(
                database.findByText(searchOrderType, text, page, perPage),
                api.findByText(searchOrderType, text, page, perPage)
                        .observeOn(Schedulers.newThread())
                        .doOnNext { database.updateCache(searchOrderType, text, page, it) }
        )
                .filter { photos -> !photos.isEmpty() }
                .first(ArrayList())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map { CatImageMapper.transform(it) }
    }

    override fun findById(catImageId: CatImageId): Observable<CatImageDomainModel> {
        return database.findById(catImageId.value)
                .observeOn(AndroidSchedulers.mainThread())
                .map { CatImageMapper.transform(it) }
    }

    override fun clearCache(searchOrderType: SearchOrderType, text: String): Single<Int> {
        return database.clearCache(searchOrderType, text)
                .observeOn(Schedulers.newThread())
    }

}