package com.itechpro.domain.repository.opportunity_project
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity_project.OpportunityProject


interface OpportunityProjectAddEditRepository {

    suspend fun getCheckKey(obj: String,mode: String,code: String,ido: String,authen: String): NetworkResponse<List<OpportunityProject>>

    suspend fun addEditOpportunitiesProject(url: String, obj: OpportunityProject, authen: String): NetworkResponse<List<OpportunityProject>>


}
