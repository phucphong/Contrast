package com.itechpro.data.repository.opportunity









import com.itechpro.data.api.oder.OderAPI
import com.itechpro.data.api.opportunity_project.OpportunityProjectAPI

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity_project.OpportunityProject
import com.itechpro.domain.repository.opportunity_project.OpportunityProjectRepository


import javax.inject.Inject


class OpportunityProjectRepositoryImpl @Inject constructor(
    private val api: OpportunityProjectAPI
) : OpportunityProjectRepository {
    override suspend fun getOpportunitiesProject(
        obj: String,
        mode: String,
        startDate: String,
        endDate: String,
        searchKey: String,
        authen: String
    ): NetworkResponse<List<OpportunityProject>> {
        val response = api.getOpportunitiesProject(obj,mode,startDate, endDate,searchKey,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getOpportunitiesProjectByIDCustomer(
        obj: String,
        mode: String,
        customerId: String,
        searchKey: String,
        authen: String
    ): NetworkResponse<List<OpportunityProject>> {
        val response = api.getOpportunitiesProjectByIDCustomer(obj,mode,customerId,searchKey,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun deleteOpportunitiesProject(
        obj: String,
        mode: String,
        ids: String,
        mamenu: String,
        os: String,
        device: String,
        content: String,
        authen: String
    ): NetworkResponse<List<OpportunityProject>> {
        val response = api.deleteOpportunitiesProject(obj,mode,ids,mamenu,os,device,content,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


}
