package com.itechpro.data.api.opportunity_project





import com.itechpro.domain.model.opportunity_project.OpportunityProject

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
interface OpportunityProjectDetailAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend   fun getOpportunitiesProjectDetail(
        @Query("obj") obj: String?,
        @Query("mode") getbyid: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<OpportunityProject>>


}