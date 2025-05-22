package com.itechpro.data.repository.opportunity








import com.itechpro.data.api.oder.OderAPI

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.oder.Order
import com.itechpro.domain.repository.OderRepository


import javax.inject.Inject


class OpportunityDetailRepositoryImpl @Inject constructor(
    private val api: OderAPI
) : OderRepository {



    override suspend fun getOderByConfirm(
        obj: String,
        mode: String,
        startDate: String,
        endDate: String,
        typeAccount: String,
        confirm: String,
        authen: String
    ): NetworkResponse<List<Order>> {
        val response = api.getOderByConfirm(obj,mode,startDate, endDate,typeAccount,confirm,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getOderByTypeAccount(
        obj: String,
        mode: String,
        startDate: String,
        endDate: String,
        customerId: String,
        typeAccount: String,

        authen: String
    ): NetworkResponse<List<Order>> {
        val response = api.getOderByTypeAccount(obj,mode,startDate, endDate,customerId,typeAccount,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


}
