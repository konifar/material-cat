package com.konifar.materialcat.presentation.gallery

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.domain.usecase.GetCatImageUseCase
import com.konifar.materialcat.presentation.photodetail.PhotoDetailViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class PhotoDetailPresenter
@Inject constructor(
        private val getCatImageUseCase: GetCatImageUseCase,
        private val compositeDisposable: CompositeDisposable
) {

    lateinit var viewModel: PhotoDetailViewModel

    fun requestGet(catImageId: CatImageId) {
        compositeDisposable.add(
                getCatImageUseCase.requestGet(catImageId)
                        .subscribeBy(
                                onNext = { viewModel.setCatImage(it) },
                                onError = { handleError() }
                        )
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun handleError() {
        // TODO
    }

}