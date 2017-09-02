package com.konifar.materialcat.presentation.gallery.detail

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.konifar.materialcat.BR
import com.konifar.materialcat.domain.model.CatImage

class PhotoDetailViewModel : BaseObservable() {

    @Bindable
    var imageUrl: String = ""

    @Bindable
    var title: String = ""

    fun setCatImage(catImage: CatImage) {
        imageUrl = catImage.imageUrl
        title = catImage.title

        notifyPropertyChanged(BR.imageUrl)
        notifyPropertyChanged(BR.title)
    }

}