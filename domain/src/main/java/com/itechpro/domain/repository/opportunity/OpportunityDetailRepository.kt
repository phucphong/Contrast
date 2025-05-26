package com.itechpro.domain.repository.opportunity



import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity


interface OpportunityDetailRepository {


    suspend fun getOpportunityDetail(obj: String,mode: String,ido: String,authen: String): NetworkResponse<List<Opportunity>>


}
