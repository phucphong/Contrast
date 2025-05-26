package com.itechpro.data.repository.opportunity








import com.itechpro.data.api.opportunity.OpportunityDetailAPI

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.repository.opportunity.OpportunityDetailRepository


import javax.inject.Inject


class OpportunityProjectDetailRepositoryImpl @Inject constructor(
    private val api: OpportunityDetailAPI
) : OpportunityDetailRepository {
    override suspend fun getOpportunitiesProjectDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Opportunity>> {
        val response = api.getOpportunitiesProjectDetail(obj,mode,ido,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


}
