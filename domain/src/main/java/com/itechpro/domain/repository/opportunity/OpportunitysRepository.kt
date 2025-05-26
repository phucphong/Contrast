package com.itechpro.domain.repository.opportunity



import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity


interface OpportunitysRepository {

    suspend fun getOpportunityProcess(obj: String,mode: String,authen: String
    ): NetworkResponse<List<Category>>

    suspend fun getOpportunity(obj: String,mode: String,startDate: String,endDate: String,searchKey: String,authen: String): NetworkResponse<List<Opportunity>>


    suspend fun getOpportunityByIDCustomer(obj: String,mode: String,customerId: String,searchKey: String,authen: String): NetworkResponse<List<Opportunity>>
    suspend fun deleteOpportunity(obj: String,mode: String,ids: String,mamenu: String,os: String,device: String,content: String,authen: String): NetworkResponse<List<Opportunity>>




}
