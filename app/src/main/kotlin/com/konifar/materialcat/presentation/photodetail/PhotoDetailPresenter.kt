package com.konifar.materialcat.presentation.photodetail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.domain.usecase.GetCatImageUseCase
import com.konifar.materialcat.presentation.photodetail.model.converter.PhotoDetailPresentationModelConverter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject


class PhotoDetailPresenter
@Inject constructor(
        private val getCatImageUseCase: GetCatImageUseCase,
        private val compositeDisposable: CompositeDisposable
) : PhotoDetailContract.Presenter {

    lateinit var view: PhotoDetailContract.View

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    override fun setUp(view: PhotoDetailContract.View) {
        this.view = view
    }

    override fun requestGetCatImage(id: CatImageId) {
        compositeDisposable.add(
                getCatImageUseCase.requestGet(id)
                        .subscribeBy(
                                onNext = { view.showCatImage(PhotoDetailPresentationModelConverter.convert(it)) },
                                onError = { handleError() }
                        )
        )
    }

    fun handleError() {
        // TODO
    }

}