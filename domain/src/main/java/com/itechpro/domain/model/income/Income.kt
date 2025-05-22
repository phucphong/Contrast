package com.itechpro.domain.model.income



data class Income (
    val iddondathang: String,
    val madonhangdathang: String,
    val ngaytao: String,
    val ngaythanhtoandonhang: String?, // có thể null
    val trangthai: String,
    val trangthaitxt: String,
    val tongdiem: Double,
    val tonghoahong: Double,
    val sotienhuong: Double?,
    val sanphams: List<IncomeProduct>
)