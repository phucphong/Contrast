package com.itechpro.domain.repository.opportunity




import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity


interface OpportunityRepository {

    suspend fun getCheckKey(obj: String,mode: String,code: String,ido: String,authen: String): NetworkResponse<List<Opportunity>>
    suspend fun addEditOpportunity(url: String, obj: Opportunity, authen: String): NetworkResponse<List<Opportunity>>


}
