package com.itechpro.domain.usecase.share_product_income



import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.income.Income
import com.itechpro.domain.model.network.NetworkResponse

import com.itechpro.domain.model.product.Product
import com.itechpro.domain.repository.ShareProductInComeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ShareProductInComeUseCase @Inject constructor(
    private val repository: ShareProductInComeRepository,

    ) {



    fun getProductsByIdParent(
        type: String, idParent: String,  searchText: String, authen: String
    ): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result =   repository.getProductsByIdParent(type, idParent, searchText,authen)

            val filteredResult = when (result) {
                is NetworkResponse.Success -> {
                    val filteredList = result.data.filter { (it.sotienhoahong ?: 0.0) > 0.0 }
                    NetworkResponse.Success(filteredList)
                }
                else -> result
            }

            emit(filteredResult)
        }.flowOn(Dispatchers.IO)
    }




    fun generateCategory(

    ): List<Category> {

        return  listOf(Category(code = "all"), Category(code = "payment_waiting"), Category(code = "paymented"))
    }
    fun getReportShareLink(
        mode: String, startDate: String, endDate: String, authen: String
    ): Flow<NetworkResponse<Product?>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getReportShareLink(mode, startDate, endDate, authen)) {
                is NetworkResponse.Success -> {
                    val obj: List<Product> = result.data
                    val status = obj.firstOrNull()
                    emit(NetworkResponse.Success(status))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }

                else -> Unit
            }
        }.flowOn(Dispatchers.IO)
    }



    fun getActualCommissionByIdOder(
        status: String,
        mode: String,
        startDate: String,
        endDate: String,
        authen: String
    ): Flow<NetworkResponse<List<Income>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getActualCommissionByIdOder(mode, startDate, endDate, authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {
                if (status.lowercase() != "all") {
                    val filtered = result.data.filter {
                        it.trangthai.lowercase().contains(status.lowercase())
                    }
                    NetworkResponse.Success(filtered)
                } else {
                    result
                }
            }

            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)
//val result =   repository.getLikeView(obj,mode, typeAccount, authen)



}
