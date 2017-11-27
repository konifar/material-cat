package com.konifar.materialcat.domain.usecase

import com.konifar.materialcat.domain.repository.CatImageFlickrRepository
import com.konifar.materialcat.domain.valueobject.SearchOrderType
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class GetCatImagesUseCaseImplTest {

    @Mock
    private lateinit var catImageFlickrRepository: CatImageFlickrRepository

    private lateinit var getCatImagesUseCaseImpl: GetCatImagesUseCaseImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getCatImagesUseCaseImpl = GetCatImagesUseCaseImpl(catImageFlickrRepository)
    }

    @Test
    fun testRequestGetPopular() {
        // Setup
        `when`(catImageFlickrRepository.findByText(anyString(), anyString(), anyInt(), anyInt())).thenReturn(Observable.empty())

        // Execute
        getCatImagesUseCaseImpl.requestGetPopular(1, false).subscribe()

        // Verify
        verify(catImageFlickrRepository).findByText(SearchOrderType.POPULAR.flickrSortString, "cat", 1, 36)
    }
    
}