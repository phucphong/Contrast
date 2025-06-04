package com.itechpro.domain.usecase.contact

import com.itechpro.domain.model.cart.CartItem
import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.contact.ContactType
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.repository.contact.ContactListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContactListUseCase @Inject constructor(
    private val repository: ContactListRepository,

    ) {





    fun getContactOpportunityNotImplement( opportunityID: String,authen: String): Flow<NetworkResponse<List<Contact>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result =  repository.getContactOpportunityNotImplement(ContactType.CONTACT_OPPORTUNITY_NOT_IMPLEMENT.obj,ContactType.CONTACT_OPPORTUNITY_NOT_IMPLEMENT.mode,opportunityID,authen)

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getContactProjectNotImplement( projectId: String,authen: String): Flow<NetworkResponse<List<Contact>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result =  repository.getContactOpportunityNotImplement(ContactType.CONTACT_PRJOECT_NOT_IMPLEMENT.obj,ContactType.CONTACT_PRJOECT_NOT_IMPLEMENT.mode,projectId,authen)

            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getContactByCustomerId( customerId: String,authen: String): Flow<NetworkResponse<List<Contact>>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result =  repository.getContactOpportunityNotImplement(ContactType.CONTACT_CUSTOMER.obj,ContactType.CONTACT_CUSTOMER.mode,customerId,authen)

            emit(result)
        }.flowOn(Dispatchers.IO)
    }




    fun deleteContacts(
        obj: String, mode: String,ids: String, mamenu: String, os: String,device: String,content: String,authen: String
    ): Flow<NetworkResponse<List<Contact>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.deleteContacts(obj,mode, ids,mamenu,os,device,content,authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {
                NetworkResponse.Success(result.data)
            }
            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)
    fun saveContactToOpportunityProject(type:String,
        obj: Contact, mamenu: String, os: String,device: String,content: String,authen: String
    ): Flow<NetworkResponse<List<Contact>>> = flow {
        emit(NetworkResponse.Loading)

        val result = repository.saveContactToOpportunityProject(if(type=="cohoikinhdoanh") ContactType.ADD_CONTACT_OPPORTUNITY.endpoint else ContactType.ADD_CONTACT_PRJOECT.endpoint, obj,mamenu,os,device,content,authen)

        val finalResult = when (result) {
            is NetworkResponse.Success -> {
                NetworkResponse.Success(result.data)
            }
            is NetworkResponse.Error -> NetworkResponse.Error(result.message)

            else -> result
        }

        emit(finalResult)
    }.flowOn(Dispatchers.IO)



    fun idsContactChecked(listModel: List<Contact>): String {
        return listModel
            .filter { it.checked == true && !it.id.isNullOrBlank() } // chỉ lấy contact được chọn và có id
            .map { it.id!!.replace(".0", "") } // bỏ ".0" nếu có
            .distinct() // loại bỏ trùng
            .joinToString(",") // nối thành chuỗi
    }



}
