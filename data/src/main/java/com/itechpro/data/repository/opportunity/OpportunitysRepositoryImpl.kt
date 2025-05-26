package com.itechpro.data.repository.opportunity









import com.itechpro.data.api.opportunity.OpportunityAPI
import com.itechpro.domain.model.category.Category

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.repository.opportunity.OpportunityRepository


import javax.inject.Inject


class OpportunityRepositoryImpl @Inject constructor(
    private val api: OpportunityAPI
) : OpportunityRepository {
    override suspend fun getOpportunityProcess(
        obj: String,
        mode: String,
        authen: String
    ): NetworkResponse<List<Category>> {
        val response = api.getOpportunityProcess(obj,mode,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getOpportunity(
        obj: String,
        mode: String,
        startDate: String,
        endDate: String,
        searchKey: String,
        authen: String
    ): NetworkResponse<List<Opportunity>> {
        val response = api.getOpportunity(obj,mode,startDate, endDate,searchKey,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getOpportunityByIDCustomer(
        obj: String,
        mode: String,
        customerId: String,
        searchKey: String,
        authen: String
    ): NetworkResponse<List<Opportunity>> {
        val response = api.getOpportunityByIDCustomer(obj,mode,customerId,searchKey,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun deleteOpportunity(
        obj: String,
        mode: String,
        ids: String,
        mamenu: String,
        os: String,
        device: String,
        content: String,
        authen: String
    ): NetworkResponse<List<Opportunity>> {
        val response = api.deleteOpportunity(obj,mode,ids,mamenu,os,device,content,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


}
