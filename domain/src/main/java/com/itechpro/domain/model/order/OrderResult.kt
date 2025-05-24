package com.itechpro.domain.model.order

data class OrderResult(
    val items: List<Order>,
    val oderInfo: Order?=null,
    val totalCount: Int?=0,



    )

