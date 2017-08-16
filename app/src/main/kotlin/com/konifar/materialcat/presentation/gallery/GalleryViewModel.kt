package com.konifar.materialcat.presentation.gallery

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.konifar.materialcat.BR

class GalleryViewModel : BaseObservable() {

    @Bindable
    var loadingVisibility: Int = View.GONE

    @Bindable
    var contentVisibility: Int = View.GONE

    @Bindable
    var loadingFooterVisibility: Int = View.GONE

    @Bindable
    var swipeRefreshing: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.swipeRefreshing)
        }

    val itemViewModels: MutableList<GalleryItemViewModel> = mutableListOf()

    fun toggleLoading(visible: Boolean) {
        if (visible) {
            contentVisibility = View.GONE
            loadingVisibility = View.VISIBLE
        } else {
            contentVisibility = View.VISIBLE
            loadingVisibility = View.GONE
        }

        notifyPropertyChanged(BR.contentVisibility)
        notifyPropertyChanged(BR.loadingVisibility)
    }

    fun toggleFooterLoading(visible: Boolean) {
        loadingFooterVisibility = if (visible) View.VISIBLE else View.GONE
        notifyPropertyChanged(BR.loadingFooterVisibility)
    }

}