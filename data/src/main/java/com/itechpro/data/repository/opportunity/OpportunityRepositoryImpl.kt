package com.itechpro.data.repository.opportunity











import com.itechpro.data.api.opportunity.OpportunityAPI

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.repository.opportunity.OpportunityRepository
import com.itechpro.domain.repository.opportunity.OpportunitysRepository


import javax.inject.Inject


class OpportunityRepositoryImpl @Inject constructor(
    private val api: OpportunityAPI
) : OpportunityRepository {
    override suspend fun getCheckKey(
        obj: String,
        mode: String,
        code: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Opportunity>> {
        val response = api.getCheckKey(obj,mode,code,ido,authen)
        return if (response.isSuccessful) {

            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }


    override suspend fun addEditOpportunity(
        url: String,
        obj: Opportunity,
        authen: String
    ): NetworkResponse<List<Opportunity>> {
        return try {
            val response = api.addEditOpportunity(url, obj,authen)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")


        }
    }


}
