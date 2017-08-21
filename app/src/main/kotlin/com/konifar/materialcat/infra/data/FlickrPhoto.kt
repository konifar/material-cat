package com.konifar.materialcat.infra.data

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.Table
import com.squareup.moshi.Json

@Table
class FlickrPhoto {
    @Json(name = "id") @Column(indexed = true) @JvmField
    var id: String = ""
    @Json(name = "owner") @Column @JvmField
    var owner: String = ""
    @Json(name = "secret") @Column @JvmField
    var secret: String = ""
    @Json(name = "server") @Column @JvmField
    var server: String = ""
    @Json(name = "farm") @Column @JvmField
    var farm: Int = 0
    @Json(name = "title") @Column(indexed = true) @JvmField
    var title: String = ""
    @Json(name = "ispublic") @Column @JvmField
    var isPublic: Int = 0
    @Json(name = "isfriend") @Column @JvmField
    var isFriend: Int = 0
    @Json(name = "isfamily") @Column @JvmField
    var isFamily: Int = 0
    @Column(indexed = true) @JvmField
    var type: String = ""
}