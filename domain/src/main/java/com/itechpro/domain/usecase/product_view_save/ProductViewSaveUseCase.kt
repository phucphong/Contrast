package com.itechpro.domain.usecase.product_view_save





import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.income.Income
import com.itechpro.domain.model.network.NetworkResponse

import com.itechpro.domain.model.product.Product
import com.itechpro.domain.repository.ProductViewSaveRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductViewSaveUseCase @Inject constructor(
    private val repository: ProductViewSaveRepository,

    ) {



    fun getLikeView(
        obj: String, mode: String,typeAccount: String,code: String, authen: String
    ): Flow<NetworkResponse<List<Product>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getLikeView(obj,mode, typeAccount, authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {

                    val filtered = result.data.filter {
                        it.loaisanpham.lowercase().contains(code.lowercase())
                    }
                    NetworkResponse.Success(filtered)

            }

            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)



}
