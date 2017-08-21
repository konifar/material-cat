package com.konifar.materialcat.presentation.gallery

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.konifar.materialcat.domain.model.CatImage
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase
import com.konifar.materialcat.presentation.ListObserver
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class GalleryPresenter
@Inject constructor(
        private val getCatImagesUseCase: GetCatImagesUseCase,
        private val compositeDisposable: CompositeDisposable,
        private val eventBus: EventBus
) : LifecycleObserver {

    companion object {
        private val PER_PAGE = 36
    }

    lateinit var navigator: GalleryPageNavigator

    lateinit var listObserver: ListObserver

    lateinit var viewModel: GalleryViewModel

    fun requestGetNew(page: Int, isRefreshing: Boolean = false) {
        showLoading(page, isRefreshing)
        getCatImagesUseCase.requestGetNew(page, PER_PAGE)
    }

    fun requestGetPopular(page: Int, isRefreshing: Boolean = false) {
        showLoading(page, isRefreshing)
        getCatImagesUseCase.requestGetPopular(page, PER_PAGE)
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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: GetCatImagesUseCase.GetPopularCatImagesSuccessEvent) {
        eventBus.removeStickyEvent(event)
        renderData(event.catImages, event.page)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: GetCatImagesUseCase.GetPopularCatImagesFailureEvent) {
        eventBus.removeStickyEvent(event)
        hideLoading(event.page)
        // TODO Show error message
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: GetCatImagesUseCase.GetNewCatImagesSuccessEvent) {
        eventBus.removeStickyEvent(event)
        renderData(event.catImages, event.page)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: GetCatImagesUseCase.GetNewCatImagesFailureEvent) {
        eventBus.removeStickyEvent(event)
        hideLoading(event.page)
        // TODO Show error message
    }

    private fun renderData(catImages: List<CatImage>, page: Int) {
        if (page == 1) viewModel.itemViewModels.clear()
        viewModel.itemViewModels.addAll(catImages.map { GalleryItemViewModel(it) })
        listObserver.notifyDataSetChanged()
        hideLoading(page)
        viewModel.swipeRefreshing = false
    }

}