package com.konifar.materialcat.presentation.photodetail

import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.presentation.photodetail.model.PhotoDetailPresentationModel

interface PhotoDetailContract {

    interface Presenter {

        fun onDestroy()

        fun setUp(view: View)

        fun requestGetCatImage(id: CatImageId)

    }

    interface View {

        fun showCatImage(model: PhotoDetailPresentationModel)

    }

}