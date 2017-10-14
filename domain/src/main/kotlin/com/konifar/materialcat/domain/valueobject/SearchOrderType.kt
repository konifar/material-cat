package com.konifar.materialcat.domain.valueobject

enum class SearchOrderType(val flickrSortString: String) {

    POPULAR("interestingness-desc"),
    NEW("date-posted-desc");

}