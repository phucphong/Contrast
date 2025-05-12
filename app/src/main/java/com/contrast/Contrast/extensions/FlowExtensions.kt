package com.contrast.Contrast.extensions


import com.itechpro.domain.model.network.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

suspend inline fun <T> Flow<NetworkResponse<T>>.collectResponse(
    dispatcher: CoroutineDispatcher,
    crossinline onSuccess: (T) -> Unit,
    crossinline onError: (String) -> Unit = {},
    crossinline onLoading: () -> Unit = {}
) {
    withContext(dispatcher) {
        try {
            collect { result ->
                when (result) {
                    is NetworkResponse.Loading -> onLoading()
                    is NetworkResponse.Success -> onSuccess(result.data)
                    is NetworkResponse.Error -> onError(result.message)
                }
            }
        } catch (e: Exception) {
            onError("Kết nối thất bại: ${e.localizedMessage}")
        }
    }
}