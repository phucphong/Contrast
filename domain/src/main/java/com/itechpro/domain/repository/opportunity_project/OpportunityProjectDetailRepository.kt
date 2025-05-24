package com.itechpro.domain.repository.opportunity_project



import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity_project.OpportunityProject


interface OpportunityProjectDetailRepository {


    suspend fun getOpportunitiesProjectDetail(obj: String,mode: String,ido: String,authen: String): NetworkResponse<List<OpportunityProject>>


}
