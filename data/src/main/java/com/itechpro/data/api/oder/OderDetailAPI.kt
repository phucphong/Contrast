package com.itechpro.data.api.oder



import com.itechpro.domain.model.oder.OderTable

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
interface OderDetailAPI {




    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun getOderById(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<OderTable>





}