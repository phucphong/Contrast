package com.itechpro.domain.usecase.category

import com.itechpro.domain.model.Category
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
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

    data class CategoryAffiliateConfig(
        val objApi: String,
        val modeApi: String,
        val type: String,
        val tabs: List<Category>
    )

    private data class ConfigData(
        val objApi: String,
        val modeApi: String,
        val type: String,
        val tabs: List<Category>
    )




}
