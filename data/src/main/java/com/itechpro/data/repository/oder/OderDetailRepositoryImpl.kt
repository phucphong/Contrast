package com.itechpro.data.repository.oder
import com.itechpro.data.api.oder.OderDetailAPI

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.order.Order
import com.itechpro.domain.model.order.OrderTable
import com.itechpro.domain.repository.oder.OderDetailRepository
import javax.inject.Inject

class OderDetailRepositoryImpl @Inject constructor(
    private val api: OderDetailAPI
) : OderDetailRepository {
    override suspend fun getOderDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<OrderTable> {
        val response = api.getOderDetail(obj,mode,ido,authen)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error("Cart data is null")
            }
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }





}
