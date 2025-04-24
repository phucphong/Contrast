package com.itechpro.domain.usecase.evaluate





import com.itechpro.domain.model.evaluate.Evaluate
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.evaluate.EvaluateResult

import com.itechpro.domain.repository.EvaluateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EvaluateUseCase @Inject constructor(
    private val repository: EvaluateRepository,

    ) {


    fun getEvaluates(
        offline: Boolean,
        idEvaluate: String,
        count: String,
        authen: String
    ): Flow<NetworkResponse<EvaluateResult>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getEvaluatesOff(idEvaluate, count)
            } else {
                repository.getEvaluates(idEvaluate, count, authen)
            }

            when (result) {
                is NetworkResponse.Success -> {
                    val list = result.data.lst_danhgia
                    val ratingScore = result.data.diemdanhgia?:0f
                    val customResult = EvaluateResult(
                        totalEvaluate = list.size,
                        evaluateList = list,
                        ratingScore = ratingScore
                    )
                    emit(NetworkResponse.Success(customResult))
                }

                is NetworkResponse.Error -> emit(result)
                is NetworkResponse.Loading -> emit(result)
            }
        }.flowOn(Dispatchers.IO)
    }



    fun addEditLike(
        url: String,
        obj: Product,
        authen: String
    ): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val result = repository.addEditLike(url, obj, authen)
                emit(result)
            } catch (e: Exception) {
                emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
            }
        }.flowOn(Dispatchers.IO)
    }



}
