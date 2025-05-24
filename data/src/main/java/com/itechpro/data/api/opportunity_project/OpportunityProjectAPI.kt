package com.itechpro.data.api.opportunity_project




import com.itechpro.domain.model.opportunity_project.OpportunityProject

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface OpportunityProjectAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getOpportunitiesProject(
        @Query("obj") obj: String?,
        @Query("mode") getallbyidcongty: String?,
        @Query("tungay") tungay: String?,
        @Query("denngay") denngay: String?,
        @Query("tukhoa") tukhoa: String?,
        @Header("Authorization") authen: String?
    ): Response<List<OpportunityProject>>



    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getOpportunitiesProjectByIDCustomer(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("idkhachhang") idCustomer: String?,
        @Query("tukhoa") key: String?,
        @Header("Authorization") authen: String?
    ): Response<List<OpportunityProject>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun deleteOpportunitiesProject(
        @Query("obj") obj: String?,
        @Query("mode") deletes: String?,
        @Query("ids") ids: String?,
        @Query("mamenu") mamenu: String?,
        @Query("os") os: String?,
        @Query("device") device: String?,
        @Query("noidungchinh") noidungchinh: String?,
        @Header("Authorization") authen: String?
    ): Response<List<OpportunityProject>>





}