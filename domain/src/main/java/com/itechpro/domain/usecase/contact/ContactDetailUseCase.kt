package com.itechpro.domain.usecase.contact
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.contact.ContactResult
import com.itechpro.domain.model.contact.ContactType
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.opportunity.OpportunityResult

import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.repository.contact.ContactDetailRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContactDetailUseCase @Inject constructor(
    private val repository: ContactDetailRepository,

    ) {

    fun getContactDetail(

        ido: String,
        authen: String
    ): Flow<NetworkResponse<Contact?>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (val result = repository.getContactDetail(ContactType.CONTACT_DETAIL.obj, ContactType.CONTACT_DETAIL.mode, ido, authen)) {
                is NetworkResponse.Success -> {
                    val Contact = result.data.firstOrNull()
                    emit(NetworkResponse.Success(Contact))
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





    fun getCallByContact(
        ido: String,

        authen: String
    ): Flow<NetworkResponse<ContactResult>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (
                val result = repository.getCallByContact(
                    ContactType.CONTACT_CALL.obj, ContactType.CONTACT_CALL.mode,
                    ido,
                    authen
                )
            ) {
                is NetworkResponse.Success -> {
                    val items = result.data
                    val totalCount = items.size
                    val opportunityResult = ContactResult(
                        items = items,
                        totalCount = totalCount
                    )
                    emit(NetworkResponse.Success(opportunityResult))
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

    fun getEmailByContact(
        ido: String,

        authen: String
    ): Flow<NetworkResponse<ContactResult>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (
                val result = repository.getEmailByContact(
                    ContactType.CONTACT_EMAIL.obj, ContactType.CONTACT_EMAIL.mode,
                    ido,
                    authen
                )
            ) {
                is NetworkResponse.Success -> {
                    val items = result.data
                    val totalCount = items.size
                    val opportunityResult = ContactResult(
                        items = items,
                        totalCount = totalCount
                    )
                    emit(NetworkResponse.Success(opportunityResult))
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

    fun getSMSByContact(
        ido: String,

        authen: String
    ): Flow<NetworkResponse<ContactResult>> {
        return flow {
            emit(NetworkResponse.Loading)
            when (
                val result = repository.getSMSByContact(
                    ContactType.CONTACT_SMS.obj, ContactType.CONTACT_SMS.mode,
                    ido,
                    authen
                )
            ) {
                is NetworkResponse.Success -> {
                    val items = result.data
                    val totalCount = items.size
                    val opportunityResult = ContactResult(
                        items = items,
                        totalCount = totalCount
                    )
                    emit(NetworkResponse.Success(opportunityResult))
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
            Category(id = "2", code = "email"),
            Category(id = "3", code = "call"),
            Category(id = "4", code = "zalo"),
            Category(id = "5", code = "facebook")
        )
    }



}
