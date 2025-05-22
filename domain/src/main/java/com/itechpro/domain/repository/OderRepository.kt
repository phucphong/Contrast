package com.itechpro.domain.repository





import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.oder.Order


interface OderRepository {


    suspend fun getOderByConfirm(obj: String,mode: String,startDate: String,endDate: String,confirm: String,typeAccount: String,authen: String): NetworkResponse<List<Order>>


    suspend fun getOderByTypeAccount(obj: String,mode: String,startDate: String,endDate: String,customerId: String,typeAccount: String,authen: String): NetworkResponse<List<Order>>





}
