package com.itechpro.data.repository.opportunity








import com.itechpro.data.api.opportunity_project.OpportunityProjectDetailAPI

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity_project.OpportunityProject
import com.itechpro.domain.repository.opportunity_project.OpportunityProjectDetailRepository


import javax.inject.Inject


class OpportunityProjectDetailRepositoryImpl @Inject constructor(
    private val api: OpportunityProjectDetailAPI
) : OpportunityProjectDetailRepository {
    override suspend fun getOpportunitiesProjectDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<OpportunityProject>> {
        val response = api.getOpportunitiesProjectDetail(obj,mode,ido,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


}
