package com.itechpro.data.api.opportunity





import com.itechpro.domain.model.contacts.Contacts
import com.itechpro.domain.model.opportunity.Opportunity
import com.itechpro.domain.model.product.AttachFile
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductOpoortutityProject

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
interface OpportunityDetailAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getOpportunityDetail(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Opportunity>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getContactByOpportunity(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("idcohoikinhdoanh") opportunityId: String?,
        @Query("tukhoa") searchText: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Contacts>>

    //  call = service.getsanphambyidcohoikinhdoanh("sanphamcohoikinhdoanh", "getallbyidcongtybyidcohoikinhdoanh", Common.idcohoikinhdoanhtab, tukhoa,"brmitechpro " + token);

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getProductByOpportunity(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("idcohoikinhdoanh") opportunityId: String?,
        @Query("tukhoa") searchText: String?,
        @Header("Authorization") authen: String?
    ): Response<List<ProductOpoortutityProject>>

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getdsfiledinhkem")
    suspend   fun getAttachByOpportunity(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<AttachFile>>


}