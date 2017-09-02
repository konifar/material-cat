package com.konifar.materialcat.infra.data

import java.util.*

enum class SearchOrderType(val flickrSortString: String) {

    POPULAR("interestingness-desc"),
    NEW("date-posted-desc");

    override fun toString(): String {
        return super.toString().toLowerCase(Locale.US)
    }

}
