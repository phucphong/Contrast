package com.itechpro.data.repository.contact











import com.itechpro.data.api.contact.ContactListAPI
import com.itechpro.domain.model.contact.Contact

import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.repository.contact.ContactListRepository


import javax.inject.Inject


class ContactListRepositoryImpl @Inject constructor(
    private val api: ContactListAPI
) : ContactListRepository {
    

    override suspend fun getContactOpportunityNotImplement(
        obj: String,
        mode: String,
        opportunityID: String,
        authen: String
    ): NetworkResponse<List<Contact>> {
        val response = api.getContactOpportunityNotImplement(obj,mode,opportunityID,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }

    override suspend fun getContactProjectNotImplement(
        obj: String,
        mode: String,
        startDate: String,
        endDate: String,
        projectId: String,
        authen: String
    ): NetworkResponse<List<Contact>> {
        val response = api.getContactProjectNotImplement(obj,mode,projectId,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }

    override suspend fun getContactByCustomerId(
        obj: String,
        mode: String,
        customerId: String,
        authen: String
    ): NetworkResponse<List<Contact>> {
        val response = api.getContactProjectNotImplement(obj,mode,customerId,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }

    override suspend fun deleteContacts(
        obj: String,
        mode: String,
        ids: String,
        mamenu: String,
        os: String,
        device: String,
        content: String,
        authen: String
    ): NetworkResponse<List<Contact>> {
        val response = api.deleteContacts(obj,mode,ids,mamenu,os,device,content,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun saveContactToOpportunityProject(
        endpoint: String,
        obj: Contact,
        mamenu: String,
        os: String,
        device: String,
        content: String,
        authen: String
    ): NetworkResponse<List<Contact>> {
        val response = api.saveContactToOpportunityProject(endpoint,obj,mamenu,os,device,content,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }


}
