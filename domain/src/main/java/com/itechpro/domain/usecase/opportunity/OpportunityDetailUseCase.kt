package com.itechpro.domain.usecase.opportunity
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.contacts.Contacts
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductOpoortutityProject
import com.itechpro.domain.repository.opportunity.OpportunityDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OpportunityDetailUseCase @Inject constructor(
    private val repository: OpportunityDetailRepository,

    ) {

    fun getOpportunityDetail(

        ido: String,
        authen: String
    ): Flow<NetworkResponse<Opportunity?>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getOpportunityDetail("cohoikinhdoanh", "getbyid", ido, authen)) {
                is NetworkResponse.Success -> {
                    val opportunity = result.data.firstOrNull()
                    emit(NetworkResponse.Success(opportunity))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }
                else -> {
                    emit(NetworkResponse.Error("Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getContactByOpportunity(
        ido: String,
        searchText: String,
        authen: String
    ): Flow<NetworkResponse<List<Contacts>>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getContactByOpportunity("lienhecohoikinhdoanh", "getallbyidcongtybyidcohoikinhdoanh", ido,searchText, authen)) {
                is NetworkResponse.Success -> {

                    emit(NetworkResponse.Success(result.data))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }
                else -> {
                    emit(NetworkResponse.Error("Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }



    fun getProductByOpportunity(
        ido: String,
        searchText: String,
        authen: String
    ): Flow<NetworkResponse<List<ProductOpoortutityProject>>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getProductByOpportunity("sanphamcohoikinhdoanh", "getallbyidcongtybyidcohoikinhdoanh", ido, searchText,authen)) {
                is NetworkResponse.Success -> {

                    emit(NetworkResponse.Success(result.data))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }
                else -> {
                    emit(NetworkResponse.Error("Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }


    fun getAttachByOpportunity(
        ido: String,
        searchText: String,
        authen: String
    ): Flow<NetworkResponse<List<AttachFile>>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getAttachByOpportunity("cohoikinhdoanh", "laytatcadinhkem", ido,authen)) {
                is NetworkResponse.Success -> {
                    emit(NetworkResponse.Success(result.data))
                }
                is NetworkResponse.Error -> {
                    emit(NetworkResponse.Error(result.message))
                }
                else -> {
                    emit(NetworkResponse.Error("Unknown error"))
                }
            }
        }.flowOn(Dispatchers.IO)
    }







    fun getDefaultAffiliateTabs(): List<Category> {
        return listOf(
            Category(id = "1", code = "info"),
            Category(id = "2", code = "contact"),
            Category(id = "3", code = "product"),
            Category(id = "4", code = "attach")
        )
    }



}
