package com.konifar.materialcat.infra.repository.catphoto

import com.konifar.materialcat.domain.gallery.model.CatImage
import com.konifar.materialcat.infra.api.FlickrApiService
import com.konifar.materialcat.infra.dto.catphoto.mapper.CatImageMapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class FlickrCatImageRepositoryImpl(private val flickrApiService: FlickrApiService) : CatImageRepository {

    companion object {
        val SORT_INTERESTINGNESS_DESC = "interestingness-desc"
        val SORT_DATE_POSTED_DESC = "date-posted-desc"
    }

    override fun findByTextOrderByPopular(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        return findByText(text, page, perPage, SORT_INTERESTINGNESS_DESC)
    }

    override fun findByTextOrderByNew(text: String, page: Int, perPage: Int): Observable<List<CatImage>> {
        return findByText(text, page, perPage, SORT_DATE_POSTED_DESC)
    }

    private fun findByText(text: String, page: Int, perPage: Int, sort: String): Observable<List<CatImage>> {
        return flickrApiService.photosSearch(text, page, perPage, sort)
                .observeOn(AndroidSchedulers.mainThread())
                .map { CatImageMapper.transform(it) }
    }

}
