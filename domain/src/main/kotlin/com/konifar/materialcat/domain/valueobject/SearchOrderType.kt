package com.konifar.materialcat.domain.valueobject

import java.util.*

enum class SearchOrderType(val flickrSortString: String) {

    POPULAR("interestingness-desc"),
    NEW("date-posted-desc");

    override fun toString(): String = super.toString().toLowerCase(Locale.US)

}