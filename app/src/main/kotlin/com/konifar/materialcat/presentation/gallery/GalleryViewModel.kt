package com.konifar.materialcat.presentation.gallery

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.konifar.materialcat.BR

class GalleryViewModel : BaseObservable() {

    @Bindable
    var loadingVisibility: Int = View.GONE

    val itemViewModels: MutableList<GalleryItemViewModel> = mutableListOf()

    fun toggleLoading(visible: Boolean) {
        loadingVisibility = if (visible) View.VISIBLE else View.GONE
        notifyPropertyChanged(BR.loadingVisibility)
    }

}
