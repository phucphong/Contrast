package com.itechpro.domain.repository.opportunity



import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.oder.Order


interface OpportunityRepository {


    suspend fun getOpportunity(obj: String,mode: String,startDate: String,endDate: String,confirm: String,typeAccount: String,authen: String): NetworkResponse<List<Order>>


    suspend fun getOpportunityByTypeAccount(obj: String,mode: String,startDate: String,endDate: String,customerId: String,typeAccount: String,authen: String): NetworkResponse<List<Order>>





}
