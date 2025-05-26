package com.itechpro.domain.usecase.opportunity


import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity

import com.itechpro.domain.repository.opportunity.OpportunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpportunityAddEditUserCase @Inject constructor(
    private val repository: OpportunityRepository,

    ) {


//    fun getCheckKey(
//        obj: String, mode: String, code: String, ido: String, authen: String
//    ): Flow<NetworkResponse<String>> = flow {
//        emit(NetworkResponse.Loading)
//
//        when (val result = repository.getCheckKey(obj, mode, code, ido, authen)) {
//            is NetworkResponse.Success -> {
//                val status = result.data.firstOrNull()?.trangthai ?: ""
//                emit(NetworkResponse.Success(status))
//            }
//
//            is NetworkResponse.Error -> {
//                emit(NetworkResponse.Error(result.message))
//            }
//
//            else -> {
//                emit(NetworkResponse.Error("Không xác định kết quả"))
//            }
//        }
//    }.flowOn(Dispatchers.IO)
//
//
//    fun addEditOpportunity(
//        type: String, ido: String, order: Opportunity, authToken: String
//    ): Flow<NetworkResponse<Opportunity>> = flow {
//        emit(NetworkResponse.Loading)
//        try {
//            var url = "/ex/api/add"+type
//            if (ido != "0") {
//                url = "/ex/api/edit"+type
//            }
//            when (val result = repository.addEditOpportunity(url, order, authToken)) {
//                is NetworkResponse.Success -> {
//                    val firstItem = result.data.firstOrNull()
//                    if (firstItem != null) {
//                        emit(NetworkResponse.Success(firstItem))
//                    } else {
//                        emit(NetworkResponse.Error("Emtry"))
//                    }
//                }
//
//                is NetworkResponse.Error -> {
//                    emit(NetworkResponse.Error(result.message))
//                }
//
//                else -> Unit
//            }
//        } catch (e: Exception) {
//            emit(NetworkResponse.Error("Error: ${e.localizedMessage ?: "Không xác định"}"))
//        }
//    }.flowOn(Dispatchers.IO)


}
