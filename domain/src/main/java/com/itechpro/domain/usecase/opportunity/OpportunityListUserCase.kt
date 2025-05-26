package com.itechpro.domain.usecase.opportunity








import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.opportunity.OpportunityResult

import com.itechpro.domain.repository.opportunity.OpportunityListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpportunityListUserCase @Inject constructor(
    private val repository: OpportunityListRepository,

    ) {

    fun getOpportunityProcess( authen: String): Flow<NetworkResponse<List<Category>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result =  repository.getOpportunityProcess(CategoryType.OPPORTUNITY_PROCESS.obj,CategoryType.OPPORTUNITY_PROCESS.mode,authen)

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getOpportunity(
        obj: String,
        startDate: String,
        endDate: String,
        searchKey: String,
        authen: String,
        statusId: String = "0",
        categoryList: List<Category>
    ): Flow<NetworkResponse<OpportunityResult>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getOpportunity(
            obj = obj,
            mode = "getallbyidcongty",
            startDate = startDate,
            endDate = endDate,
            searchKey = searchKey,
            authen = authen
        )

        val finalResult: NetworkResponse<OpportunityResult> = when (result) {
            is NetworkResponse.Success -> {
                val fullData = result.data

                val cleanStatusId = statusId.trim()

                val updatedCategories = categoryList.map { category ->
                    val count = if (category.id == "-1" || category.id == "0") {
                        fullData.size
                    } else {
                        fullData.count { it.idtrangthai == category.id }
                    }
                    category.copy(quantity = count)
                }

                val filteredList = if (cleanStatusId == "0" || cleanStatusId == "-1") {
                    fullData
                } else {
                    fullData.filter { it.idtrangthai == cleanStatusId }
                }

                NetworkResponse.Success(
                    OpportunityResult(
                        items = filteredList,
                        categories = updatedCategories
                    )
                )
            }

            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> NetworkResponse.Error("Unexpected response") // 👈 Fix chỗ else

        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)


    fun getOpportunityByIDCustomer(
        obj: String, mode: String,customerId: String, searchKey: String,authen: String,
        statusId: String = "0",
        categoryList: List<Category>
    ): Flow<NetworkResponse<OpportunityResult>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.getOpportunityByIDCustomer(obj,mode, customerId,searchKey,authen)

        val finalResult: NetworkResponse<OpportunityResult> = when (result) {
            is NetworkResponse.Success -> {
                val fullData = result.data

                val cleanStatusId = statusId.trim()

                val updatedCategories = categoryList.map { category ->
                    val count = if (category.id == "-1" || category.id == "0") {
                        fullData.size
                    } else {
                        fullData.count { it.idtrangthai == category.id }
                    }
                    category.copy(quantity = count)
                }

                val filteredList = if (cleanStatusId == "0" || cleanStatusId == "-1") {
                    fullData
                } else {
                    fullData.filter { it.idtrangthai == cleanStatusId }
                }

                NetworkResponse.Success(
                    OpportunityResult(
                        items = filteredList,
                        categories = updatedCategories
                    )
                )
            }

            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> NetworkResponse.Error("Unexpected response") // 👈 Fix chỗ else

        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)



    fun deleteOpportunity(
        obj: String, mode: String,ids: String, mamenu: String, os: String,device: String,content: String,authen: String
    ): Flow<NetworkResponse<List<Opportunity>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.deleteOpportunity(obj,mode, ids,mamenu,os,device,content,authen)

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
