package com.konifar.materialcat.domain.model

import com.konifar.materialcat.domain.DomainModel

data class CatImage(
        override val id: CatImageId,
        val title: String,
        val imageUrl: String
) : DomainModel<CatImageId>()
