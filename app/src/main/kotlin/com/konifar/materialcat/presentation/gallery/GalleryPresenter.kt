package com.konifar.materialcat.presentation.gallery

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase
import com.konifar.materialcat.presentation.ListObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class GalleryPresenter
@Inject constructor(
        private val getCatImagesUseCase: GetCatImagesUseCase,
        private val compositeDisposable: CompositeDisposable
) : LifecycleObserver {

    lateinit var navigator: GalleryPageNavigator

    lateinit var listObserver: ListObserver

    lateinit var viewModel: GalleryViewModel

    fun requestGetNew(page: Int, isRefreshing: Boolean = false) {
        showLoading(page, isRefreshing)
        getCatImagesUseCase.requestGetNew(page)
                .subscribeBy(
                        onNext = { renderData(it, page) },
                        onError = { hideLoading(page) }
                )

    }

    fun requestGetPopular(page: Int, isRefreshing: Boolean = false) {
        showLoading(page, isRefreshing)
        getCatImagesUseCase.requestGetPopular(page)
                .subscribeBy(
                        onNext = { renderData(it, page) },
                        onError = { hideLoading(page) }
                )
    }

    private fun showLoading(page: Int, isRefreshing: Boolean = false) {
        if (page == 1) {
            viewModel.toggleLoading(!isRefreshing)
        } else {
            viewModel.toggleFooterLoading(true)
        }
    }

    private fun hideLoading(page: Int) {
        if (page == 1) {
            viewModel.toggleLoading(false)
        } else {
            viewModel.toggleFooterLoading(false)
        }
    }

    fun onClickItem(id: CatImageId) {
//        navigator.openDetail(viewModel.id)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun renderData(catImages: List<CatImage>, page: Int) {
        if (page == 1) viewModel.itemViewModels.clear()
        viewModel.itemViewModels.addAll(catImages.map { GalleryItemViewModel(it) })
        listObserver.notifyDataSetChanged()
        hideLoading(page)
        viewModel.swipeRefreshing = false
    }

}