package com.konifar.materialcat.infra.repository.catimage

import com.konifar.materialcat.domain.model.CatImageDomainModel
import com.konifar.materialcat.domain.repository.CatImageFlickrRepository
import com.konifar.materialcat.infra.data.mapper.CatImageDomainModelConverter
import com.konifar.materialcat.infra.repository.catimage.datasource.CatImageFlickrApiDataSource
import com.konifar.materialcat.infra.repository.catimage.datasource.CatImageFlickrDatabaseDataSource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CatImageFlickrRepositoryImpl(
        private val api: CatImageFlickrApiDataSource,
        private val database: CatImageFlickrDatabaseDataSource
) : CatImageFlickrRepository {

    override fun findByText(sortType: String, text: String, page: Int, perPage: Int): Observable<List<CatImageDomainModel>> {
        return Observable.concat(
                database.findByText(sortType, text, page, perPage),
                api.findByText(sortType, text, page, perPage)
                        .observeOn(Schedulers.newThread())
                        .doOnNext { database.updateCache(sortType, text, page, it) }
        )
                .filter { photos -> !photos.isEmpty() }
                .first(ArrayList())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map { CatImageDomainModelConverter.convert(it) }
    }

    override fun findById(id: String): Observable<CatImageDomainModel> {
        return database.findById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .map { CatImageDomainModelConverter.convert(it) }
    }

    override fun clearCache(id: String, text: String): Single<Int> {
        return database.clearCache(id, text)
                .observeOn(Schedulers.newThread())
    }

}