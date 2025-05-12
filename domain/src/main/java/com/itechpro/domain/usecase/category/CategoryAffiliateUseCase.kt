package com.itechpro.domain.usecase.category

import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.repository.CategoryAffiliateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoryAffiliateUseCase @Inject constructor(
    private val repository: CategoryAffiliateRepository,

    ) {


    fun getCategory(offline: Boolean, obj: String,mode: String,type: String,idParent: String,authen: String): Flow<NetworkResponse<List<Category>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getCategoryOff(obj, mode,type,idParent)
            } else {
                repository.getCategory(obj, mode,type,idParent,authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getProductsByIdParent(offline: Boolean,type: String,idParent: String,authen: String): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)
            val result = if (offline) {
                repository.getProductsByIdParentOff(type,idParent)
            } else {
                repository.getProductsByIdParent( type,idParent,authen)
            }

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun searchLocal(textSearch: String, list: List<Product>): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val filteredList = if (textSearch.isEmpty()) {
                list
            } else {
                list.filter { product ->
                    val name = product.ten ?: ""
                    val code = product.ma ?: ""
                    name.contains(textSearch, ignoreCase = true) || code.contains(textSearch, ignoreCase = true)
                }
            }

            emit(NetworkResponse.Success(filteredList))
        }.flowOn(Dispatchers.Default)
    }







}
