package com.itechpro.domain.model.evaluate


data class Evaluate(
    val diemdanhgia: Float? = 0f,
    var total: Int? = 0,
    var lst_danhgia: List<EvaluateDetail> = arrayListOf(),


    )
