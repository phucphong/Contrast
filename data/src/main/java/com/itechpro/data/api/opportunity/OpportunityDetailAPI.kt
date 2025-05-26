package com.itechpro.data.api.opportunity





import com.itechpro.domain.model.opportunity.Opportunity

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


}