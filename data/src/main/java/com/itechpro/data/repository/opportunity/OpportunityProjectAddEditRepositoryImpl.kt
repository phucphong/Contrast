package com.itechpro.data.repository.opportunity











import com.itechpro.data.api.opportunity_project.OpportunityProjectAddEditAPI

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity_project.OpportunityProject
import com.itechpro.domain.repository.opportunity_project.OpportunityProjectAddEditRepository


import javax.inject.Inject


class OpportunityProjectAddEditRepositoryImpl @Inject constructor(
    private val api: OpportunityProjectAddEditAPI
) : OpportunityProjectAddEditRepository {
    override suspend fun getCheckKey(
        obj: String,
        mode: String,
        code: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<OpportunityProject>> {
        val response = api.getCheckKey(obj,mode,code,ido,authen)
        return if (response.isSuccessful) {

            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }


    override suspend fun addEditOpportunitiesProject(
        url: String,
        obj: OpportunityProject,
        authen: String
    ): NetworkResponse<List<OpportunityProject>> {
        return try {
            val response = api.addEditOpportunitiesProject(url, obj,authen)
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
