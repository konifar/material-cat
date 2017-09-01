package com.konifar.materialcat.infra.repository

import com.annimon.stream.Stream
import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.infra.data.FlickrPhoto
import com.konifar.materialcat.infra.data.FlickrPhotoResponse
import com.konifar.materialcat.infra.data.mapper.CatImageMapper
import io.reactivex.Observable

class CatImageFlickerMemoryDataSource(
        private val cacheForPopularPerPage: MutableMap<Int, List<FlickrPhoto>>,
        private val cacheForNewPerPage: MutableMap<Int, List<FlickrPhoto>>,
        private val cachePerId: MutableMap<String, FlickrPhoto>
) : CatImageRepository {

    override fun findByTextOrderByPopular(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        if (cacheForPopularPerPage[page] != null) {
            return Observable.just(Stream.of(cacheForPopularPerPage[page]).map { CatImageMapper.transform(it) }.toList())
        }

        return Observable.empty()
    }

    override fun findByTextOrderByNew(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        if (cacheForNewPerPage[page] != null) {
            return Observable.just(Stream.of(cacheForNewPerPage[page]).map { CatImageMapper.transform(it) }.toList())
        }

        return Observable.empty()
    }

    override fun findById(catImageId: CatImageId): Observable<CatImage> {
        if (cachePerId[catImageId.value] != null) {
            return Observable.just(cachePerId[catImageId.value]).map { CatImageMapper.transform(it) }
        }

        return Observable.empty()
    }

    override fun updatePopularCache(response: FlickrPhotoResponse) {
        val meta = response.photos
        cacheForPopularPerPage.put(meta.page, meta.photo)
    }

    override fun updateNewCache(response: FlickrPhotoResponse) {
        val meta = response.photos
        cacheForNewPerPage.put(meta.page, meta.photo)
    }

    override fun clearPopularCache() {
        cacheForPopularPerPage.clear()
    }

    override fun clearNewCache() {
        cacheForNewPerPage.clear()
    }

}