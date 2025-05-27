package com.itechpro.domain.repository.opportunity



import com.itechpro.domain.model.contacts.Contacts
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductOpoortutityProject


interface OpportunityDetailRepository {


    suspend fun getOpportunityDetail(obj: String,mode: String,ido: String,authen: String): NetworkResponse<List<Opportunity>>
    suspend fun getContactByOpportunity(obj: String,mode: String,opportunityId: String,searchText: String,authen: String): NetworkResponse<List<Contacts>>
    suspend fun getProductByOpportunity(obj: String,mode: String,opportunityId: String,searchText: String,authen: String): NetworkResponse<List<ProductOpoortutityProject>>
    suspend fun getAttachByOpportunity(obj: String,mode: String,ido: String,authen: String): NetworkResponse<List<AttachFile>>


}
