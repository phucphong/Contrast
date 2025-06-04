package com.itechpro.data.api.contact






import com.itechpro.domain.model.contact.Contact
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.model.product.ProductOpoortutityProject

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
interface ContactDetailAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getContactDetail(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Contact>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getEmailByContact(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("ido") ido: String?,

        @Header("Authorization") authen: String?
    ): Response<List<Contact>>
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getCallByContact(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("ido") ido: String?,

        @Header("Authorization") authen: String?
    ): Response<List<Contact>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getSMSByContact(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("ido") ido: String?,

        @Header("Authorization") authen: String?
    ): Response<List<Contact>>





    @Headers("Content-Type: application/json")
    @GET("/ex/api/getdsfiledinhkem")
    suspend   fun getAttachByOpportunity(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<AttachFile>>


}