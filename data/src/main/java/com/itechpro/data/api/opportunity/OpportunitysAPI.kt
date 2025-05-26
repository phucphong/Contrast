package com.itechpro.data.api.opportunity




import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.opportunity.Opportunity

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface OpportunityAPI {

//OPPORTUNITY_PROCESS
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getOpportunityProcess(
        @Query("obj") obj: String?,
        @Query("mode") getallbyidcongty: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getOpportunity(
        @Query("obj") obj: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("tungay") tungay: String?,
        @Query("denngay") denngay: String?,
        @Query("tukhoa") tukhoa: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Opportunity>>



    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getOpportunityByIDCustomer(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("idkhachhang") idCustomer: String?,
        @Query("tukhoa") key: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Opportunity>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun deleteOpportunity(
        @Query("obj") obj: String?,
        @Query("mode") deletes: String?,
        @Query("ids") ids: String?,
        @Query("mamenu") mamenu: String?,
        @Query("os") os: String?,
        @Query("device") device: String?,
        @Query("noidungchinh") noidungchinh: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Opportunity>>





}