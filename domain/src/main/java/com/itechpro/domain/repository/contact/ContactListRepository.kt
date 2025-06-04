package com.itechpro.domain.repository.contact




import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.network.NetworkResponse


interface ContactListRepository {

    suspend fun getContactOpportunityNotImplement(obj: String,mode: String,opportunityID: String,authen: String
    ): NetworkResponse<List<Contact>>

    suspend fun getContactProjectNotImplement(obj: String,mode: String,startDate: String,endDate: String,projectId: String,authen: String): NetworkResponse<List<Contact>>


    suspend fun getContactByCustomerId(obj: String,mode: String,customerId: String,authen: String): NetworkResponse<List<Contact>>
    suspend fun deleteContacts(obj: String,mode: String,ids: String,mamenu: String,os: String,device: String,content: String,authen: String): NetworkResponse<List<Contact>>

  suspend fun saveContactToOpportunityProject(endpoint: String,obj: Contact,mamenu: String,os: String,device: String,content: String,authen: String): NetworkResponse<List<Contact>>








}
