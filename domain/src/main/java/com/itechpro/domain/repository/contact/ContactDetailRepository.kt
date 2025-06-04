package com.itechpro.domain.repository.contact






import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.network.NetworkResponse

import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.model.product.ProductOpoortutityProject


interface ContactDetailRepository {


    suspend fun getContactDetail(obj: String,mode: String,ido: String,authen: String): NetworkResponse<List<Contact>>
    suspend fun getEmailByContact(obj: String,mode: String,ido: String,authen: String): NetworkResponse<List<Contact>>
    suspend fun getCallByContact(obj: String,mode: String,ido: String,authen: String): NetworkResponse<List<Contact>>
    suspend fun getSMSByContact(obj: String,mode: String,ido: String,authen: String): NetworkResponse<List<Contact>>



}
