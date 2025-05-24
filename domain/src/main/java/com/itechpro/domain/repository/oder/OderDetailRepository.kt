package com.itechpro.domain.repository.oder

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.order.Order
import com.itechpro.domain.model.order.OrderTable


interface OderDetailRepository {


    suspend fun getOderDetail(obj: String,mode: String,ido: String,authen: String): NetworkResponse<OrderTable>




}
