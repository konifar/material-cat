package com.konifar.materialcat.presentation.gallery

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.konifar.materialcat.domain.gallery.model.CatImage
import com.konifar.materialcat.domain.gallery.model.CatImageId
import com.konifar.materialcat.domain.gallery.usecase.GetCatImagesUseCase
import com.konifar.materialcat.presentation.ListObserver
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class GalleryPresenter
@Inject constructor(
        private val getCatImagesUseCase: GetCatImagesUseCase
) : LifecycleObserver {

    companion object {
        private val PER_PAGE = 36
    }

    lateinit var navigator: GalleryPageNavigator

    lateinit var listObserver: ListObserver

    lateinit var viewModel: GalleryViewModel

    fun requestGetNew(page: Int) {
        if (page == 1) {
            viewModel.toggleLoading(true)
        }
        getCatImagesUseCase.requestGetNew(page, PER_PAGE)
    }

    fun requestGetPopular(page: Int) {
        if (page == 1) {
            viewModel.toggleLoading(true)
        }
        getCatImagesUseCase.requestGetPopular(page, PER_PAGE)
    }

    fun onClickItem(id: CatImageId) {
        TODO()
//        navigator.openDetail(viewModel.id)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (!getCatImagesUseCase.eventBus().isRegistered(this)) {
            getCatImagesUseCase.eventBus().register(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (getCatImagesUseCase.eventBus().isRegistered(this)) {
            getCatImagesUseCase.eventBus().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: GetCatImagesUseCase.GetPopularCatImagesSuccessEvent) {
        getCatImagesUseCase.eventBus().removeStickyEvent(event)
        renderData(event.catImages)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: GetCatImagesUseCase.GetPopularCatImagesFailureEvent) {
        getCatImagesUseCase.eventBus().removeStickyEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: GetCatImagesUseCase.GetNewCatImagesSuccessEvent) {
        getCatImagesUseCase.eventBus().removeStickyEvent(event)
        renderData(event.catImages)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: GetCatImagesUseCase.GetNewCatImagesFailureEvent) {
        getCatImagesUseCase.eventBus().removeStickyEvent(event)
    }

    private fun renderData(catImages: List<CatImage>) {
        viewModel.itemViewModels.addAll(catImages.map { GalleryItemViewModel(it) })
        listObserver.notifyDataSetChanged()
        viewModel.toggleLoading(false)
    }

}