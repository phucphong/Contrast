package com.itechpro.data.repository.contact
import com.itechpro.data.api.contact.ContactDetailAPI
import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.repository.contact.ContactDetailRepository
import javax.inject.Inject

class ContactDetailRepositoryImpl @Inject constructor(
    private val api: ContactDetailAPI
) : ContactDetailRepository {
    override suspend fun getContactDetail(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Contact>> {
        val response = api.getContactDetail(obj,mode,ido,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }
  override suspend fun getEmailByContact(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Contact>> {
        val response = api.getEmailByContact(obj,mode,ido,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }
  override suspend fun getCallByContact(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Contact>> {
        val response = api.getCallByContact(obj,mode,ido,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }
  override suspend fun getSMSByContact(
        obj: String,
        mode: String,
        ido: String,
        authen: String
    ): NetworkResponse<List<Contact>> {
        val response = api.getSMSByContact(obj,mode,ido,authen)
        return if (response.isSuccessful) {
            NetworkResponse.Success(response.body() ?: emptyList())
        } else {
            NetworkResponse.Error("Error: ${response.message()}")
        }
    }





}
