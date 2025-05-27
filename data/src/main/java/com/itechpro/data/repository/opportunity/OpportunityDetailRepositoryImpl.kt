package com.itechpro.data.repository.opportunity








import com.itechpro.data.api.opportunity.OpportunityDetailAPI
import com.itechpro.domain.model.contacts.Contacts

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductOpoortutityProject
import com.itechpro.domain.repository.opportunity.OpportunityDetailRepository


import javax.inject.Inject


class OpportunityDetailRepositoryImpl @Inject constructor(
    private val api: OpportunityDetailAPI
) : OpportunityDetailRepository {
    override suspend fun getOpportunityDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Opportunity>> {
        val response = api.getOpportunityDetail(obj,mode,ido,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }

    override suspend fun getContactByOpportunity(
        obj: String,
        mode: String,
        opportunityId: String,
        searchText: String,
        authen: String
    ): NetworkResponse<List<Contacts>> {
        val response = api.getContactByOpportunity(obj,mode,opportunityId,searchText,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }

    override suspend fun getProductByOpportunity(
        obj: String,
        mode: String,
        opportunityId: String,
        searchText: String,
        authen: String
    ): NetworkResponse<List<ProductOpoortutityProject>> {
        val response = api.getProductByOpportunity(obj,mode,opportunityId,searchText,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }



    override suspend fun getAttachByOpportunity(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<AttachFile>> {
        val response = api.getAttachByOpportunity(obj,mode,ido,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }


}
