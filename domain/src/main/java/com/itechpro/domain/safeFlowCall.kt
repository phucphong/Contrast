package com.itechpro.domain

import com.itechpro.domain.model.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <T> safeFlowCall(
    crossinline apiCall: suspend () -> NetworkResponse<T>
): Flow<NetworkResponse<T>> = flow {
    emit(NetworkResponse.Loading)

    when (val response = apiCall()) {
        is NetworkResponse.Success -> emit(NetworkResponse.Success(response.data))
        is NetworkResponse.Error -> emit(NetworkResponse.Error(response.message))
        is NetworkResponse.Loading -> Unit
    }
}.flowOn(Dispatchers.IO)
