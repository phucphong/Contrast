package com.itechpro.domain.model.review


data class Review(
    val diemdanhgia: Float? = 0f,
    var total: Int? = 0,
    var lst_danhgia: List<ReviewDetail> = arrayListOf(),


    )
