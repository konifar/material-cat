package com.konifar.materialcat.presentation.gallery

import android.arch.lifecycle.LifecycleObserver
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.presentation.gallery.model.GalleryItemPresentationModel

interface GalleryContract {

    interface Presenter : LifecycleObserver {

        fun onDestroy()

        fun setUp(view: View, navigator: Navigator)

        fun requestGetNew(page: Int, isRefreshing: Boolean = false)

        fun requestGetPopular(page: Int, isRefreshing: Boolean = false)

        fun onClickItem(id: CatImageId)

    }

    interface View {

        fun showCatImages(presentationModels: List<GalleryItemPresentationModel>, page: Int)

        fun showProgress(page: Int, isRefreshing: Boolean = false)

        fun hideProgress(page: Int)

    }

    interface Navigator {

        fun showDetail(id: CatImageId)

    }

}