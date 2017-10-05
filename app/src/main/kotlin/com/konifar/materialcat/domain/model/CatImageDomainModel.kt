package com.konifar.materialcat.domain.model

data class CatImageDomainModel(
        override val id: CatImageId,
        val title: String,
        val imageUrl: String
) : DomainModel<CatImageId>()
