package com.itechpro.data.api.opportunity




import com.itechpro.domain.model.opportunity.Opportunity

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface OpportunityAddEditAPI {




    // qrcode ma
    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun getCheckKey(
        @Query("obj") khachhang: String?,
        @Query("mode") mode: String?,
        @Query("ma") ten: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Opportunity>>



    @Headers("Content-Type: application/json")
    @POST
    suspend fun addEditOpportunitiesProject(
        @Url url: String,
        @Body body: Opportunity?,
        @Header("Authorization") authen: String?
    ): Response<List<Opportunity>>


}