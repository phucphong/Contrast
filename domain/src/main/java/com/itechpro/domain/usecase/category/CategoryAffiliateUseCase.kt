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

    fun generateConfig(
        displayProduct: String,
        displayService: String,
        displayPriority: String
    ): CategoryAffiliateConfig {
        val isProductEnabled = displayProduct == "1"
        val isServiceEnabled = displayService == "1"
        val isProductPriority = displayPriority == "0"

        val (objApi, modeApi, type, tabs) = when {
            isProductEnabled && isServiceEnabled && isProductPriority -> {
                ConfigData(
                    "tatcasp",
                    "modetatcasp",
                    "huuhinh",
                    listOf(Category(code = "product"), Category(code = "service"))
                )
            }

            isProductEnabled && isServiceEnabled -> {
                ConfigData(
                    "tatcadv",
                    "modetatcadv",
                    "dichvu",
                    listOf(Category(code = "service"), Category(code = "product"))
                )
            }

            isProductEnabled -> {
                ConfigData(
                    "tatcasp",
                    "modetatcasp",
                    "huuhinh",
                    listOf(Category(code = "product"))
                )
            }

            isServiceEnabled -> {
                ConfigData(
                    "tatcadv",
                    "modetatcadv",
                    "dichvu",
                    listOf(Category(code = "service"))
                )
            }

            else -> ConfigData("", "", "", emptyList())
        }

        return CategoryAffiliateConfig(
            objApi = objApi,
            modeApi = modeApi,
            type = type,
            tabs = tabs
        )
    }



}
