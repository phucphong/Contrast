package com.itechpro.domain.model.report


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Report(
    val id: String? = "",
    var ten: String? = "",
    var mota: String? = "",
    var ma: String? = "",
    var hoten: String? = "",
    var capdodaily: String? = "",
    var capdodailymoi: String? = "",
    var capdodailycu: String? = "",
    var soluongdonhangchoxacnhan: Int? = 0,
    var soluongdonhangdaxacnhan: Int? = 0,
    var tongtientruocchietkhau: Double? = 0.0,
    var tongtiensauchietkhau: Double? = 0.0,
    var sotienchitieutheogiachietkhau: Double? = 0.0,
    var sotiendlcapduoichitieutheogiachietkhau: Double? = 0.0,
    var sotienchitieutheogiagoc: Double? = 0.0,
    var sotiendlcapduoichitieutheogiagoc: Double? = 0.0,
    var phantramhuong: Double? = 0.0,
    var sotienduochuong: Double? = 0.0,
    var tongdoanhso: Double? = 0.0,

    )
