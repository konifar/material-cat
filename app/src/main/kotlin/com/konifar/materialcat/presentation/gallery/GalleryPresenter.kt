package com.konifar.materialcat.presentation.gallery

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.domain.usecase.GetCatImagesUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class GalleryPresenter
@Inject constructor(
        private val getCatImagesUseCase: GetCatImagesUseCase,
        private val compositeDisposable: CompositeDisposable
) : GalleryContract.Presenter {

    lateinit var view: GalleryContract.View

    lateinit var navigator: GalleryContract.Navigator

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    override fun setUp(view: GalleryContract.View, navigator: GalleryContract.Navigator) {
        this.view = view;
        this.navigator = navigator
    }

    override fun requestGetNew(page: Int, isRefreshing: Boolean) {
        view.showProgress(page, isRefreshing)
        getCatImagesUseCase.requestGetNew(page, isRefreshing)
                .subscribeBy(
                        onNext = { view.showCatImages(it, page) },
                        onError = { view.hideProgress(page) }
                )

    }

    override fun requestGetPopular(page: Int, isRefreshing: Boolean) {
        view.showProgress(page, isRefreshing)
        getCatImagesUseCase.requestGetPopular(page, isRefreshing)
                .subscribeBy(
                        onNext = { view.showCatImages(it, page) },
                        onError = { view.hideProgress(page) }
                )
    }

    override fun onClickItem(id: CatImageId) {
        navigator.showDetail(id)
    }

}