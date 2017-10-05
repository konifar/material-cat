package com.konifar.materialcat.presentation.gallery

import com.konifar.materialcat.domain.model.CatImageDomainModel
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase
import com.konifar.materialcat.presentation.ListObserver
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(BlockJUnit4ClassRunner::class)
class GalleryPresenterTest {

    @Mock
    lateinit var navigator: GalleryPageNavigator

    @Mock
    lateinit var listObserver: ListObserver

    @Mock
    lateinit var viewModel: GalleryViewModel

    @Mock
    lateinit var getCatImagesUseCase: GetCatImagesUseCase

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    lateinit var galleryPresenter: GalleryPresenter

    @Before
    fun setUp() {
        val testScheduler: TestScheduler = TestScheduler()
        RxJavaPlugins.reset()
        RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }

        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }

        MockitoAnnotations.initMocks(this)
        galleryPresenter = GalleryPresenter(getCatImagesUseCase, compositeDisposable)
                .apply {
                    listObserver = this@GalleryPresenterTest.listObserver
                    navigator = this@GalleryPresenterTest.navigator
                    viewModel = this@GalleryPresenterTest.viewModel
                }
    }

    @Test
    fun requestGetNew() {
        // Setup
        val list: List<CatImageDomainModel> = ArrayList()
        Mockito.doReturn(Observable.just(list)).`when`(getCatImagesUseCase).requestGetNew(1, true)

        // Exec
        galleryPresenter.requestGetNew(1, true)

        // Verify
        Mockito.verify(getCatImagesUseCase).requestGetNew(1, true)
        Mockito.verify(viewModel).toggleLoading(false)
    }

}