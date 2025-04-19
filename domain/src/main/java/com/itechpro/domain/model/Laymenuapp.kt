package com.itechpro.domain.model

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
class Laymenuapp {
    var tieude: String? = null
    var ma: String? = null
    var ten: String? = null
    var isHoatdong: Boolean = false
    var isheader: Boolean = false
    var isCheck: Boolean = false
}