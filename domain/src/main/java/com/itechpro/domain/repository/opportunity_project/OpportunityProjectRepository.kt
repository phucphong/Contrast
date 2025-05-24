package com.itechpro.domain.repository.opportunity_project



import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity_project.OpportunityProject


interface OpportunityProjectRepository {


    suspend fun getOpportunitiesProject(obj: String,mode: String,startDate: String,endDate: String,searchKey: String,authen: String): NetworkResponse<List<OpportunityProject>>


    suspend fun getOpportunitiesProjectByIDCustomer(obj: String,mode: String,customerId: String,searchKey: String,authen: String): NetworkResponse<List<OpportunityProject>>
    suspend fun deleteOpportunitiesProject(obj: String,mode: String,ids: String,mamenu: String,os: String,device: String,content: String,authen: String): NetworkResponse<List<OpportunityProject>>




}
