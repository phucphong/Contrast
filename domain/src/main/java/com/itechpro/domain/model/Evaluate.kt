package com.itechpro.domain.model

import com.itechpro.domain.model.product.EvaluateDetail


data class Evaluate(
    val diemdanhgia: String? = null,
    val noidung: String? = null,
    val diem: Double? = 0.0,
    var lst_danhgia: List<EvaluateDetail> = arrayListOf(),
    var lst_dinhkem: List<EvaluateDetail> = arrayListOf(),

    )