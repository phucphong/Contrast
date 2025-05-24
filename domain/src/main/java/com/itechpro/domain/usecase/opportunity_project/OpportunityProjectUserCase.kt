package com.itechpro.domain.usecase.opportunity_project








import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity_project.OpportunityProject

import com.itechpro.domain.repository.opportunity_project.OpportunityProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpportunityProjectUserCase @Inject constructor(
    private val repository: OpportunityProjectRepository,

    ) {

    fun getOpportunitiesProject(
        obj: String,startDate: String, endDate: String, searchKey: String,authen: String
    ): Flow<NetworkResponse<List<OpportunityProject>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getOpportunitiesProject(obj,"getallbyidcongty", startDate,  endDate,searchKey,authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {
                NetworkResponse.Success(result.data)
            }
            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)
    fun getOpportunitiesProjectByIDCustomer(
        obj: String, mode: String,customerId: String, searchKey: String,authen: String
    ): Flow<NetworkResponse<List<OpportunityProject>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getOpportunitiesProjectByIDCustomer(obj,mode, customerId,searchKey,authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {
                NetworkResponse.Success(result.data)
            }
            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)




    fun deleteOpportunitiesProject(
        obj: String, mode: String,ids: String, mamenu: String, os: String,device: String,content: String,authen: String
    ): Flow<NetworkResponse<List<OpportunityProject>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.deleteOpportunitiesProject(obj,mode, ids,mamenu,os,device,content,authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {
                NetworkResponse.Success(result.data)
            }
            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)





}
