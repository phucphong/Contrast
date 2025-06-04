package com.itechpro.data.api.contact






import com.itechpro.domain.model.contact.Contact

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactListAPI {

    //OPPORTUNITY_PROCESS
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getContactOpportunityNotImplement(
        @Query("obj") obj: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("idcohoikinhdoanh") opportunityID: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Contact>>
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getContactProjectNotImplement(
        @Query("obj") obj: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("idduan") projectId: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Contact>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getContactByCustomerId(
        @Query("obj") obj: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("idkhachhang") customerId: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Contact>>

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun deleteContacts(
        @Query("obj") obj: String?,
        @Query("mode") deletes: String?,
        @Query("ids") ids: String?,
        @Query("mamenu") mamenu: String?,
        @Query("os") os: String?,
        @Query("device") device: String?,
        @Query("noidungchinh") noidungchinh: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Contact>>

    @Headers("Content-Type: application/json")
    @POST("{endpoint}")
    suspend   fun saveContactToOpportunityProject(
        @Path("endpoint") endpoint: String,

        @Body body: Contact?,
        @Query("mamenu") mamenu: String?,
        @Query("os") os: String?,
        @Query("device") device: String?,
        @Query("noidungchinh") noidungchinh: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Contact>>



}