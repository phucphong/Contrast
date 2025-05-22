package com.itechpro.domain.model.income



data class IncomeProduct (
    var pos: Int?=0,
    val id: String,
    val tensanpham: String,
    val iddonvi: String,
    val soluong: String,
    val filetxt: String?, // có thể null
    val sotienhoahong: Double,
    val diem: Double,


    )

