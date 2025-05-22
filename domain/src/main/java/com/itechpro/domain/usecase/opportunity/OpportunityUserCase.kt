package com.itechpro.domain.usecase.opportunity








import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.oder.Order

import com.itechpro.domain.repository.OderRepository
import com.itechpro.domain.repository.opportunity.OpportunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpportunityUserCase @Inject constructor(
    private val repository: OpportunityRepository,

    ) {



    fun getOpportunity(
        startDate: String, endDate: String,confirm: String, typeAccount: String,authen: String
    ): Flow<NetworkResponse<List<Order>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getOpportunity("dailyaf","danhsachdonhang", startDate,  endDate,confirm,typeAccount,authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {
                NetworkResponse.Success(result.data)
            }
            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)



    fun getOpportunityByTypeAccount(
        startDate: String, endDate: String,customerId: String, typeAccount: String,authen: String
    ): Flow<NetworkResponse<List<Order>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getOpportunityByTypeAccount("laydsdonbanhang","modelaydsdonhangcuakhachhang", startDate,  endDate,customerId,typeAccount,authen)

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
