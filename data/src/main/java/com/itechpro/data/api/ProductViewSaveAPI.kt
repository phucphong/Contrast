package com.itechpro.data.api

import com.itechpro.domain.model.product.Product

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
interface ProductViewSaveAPI {




    @Headers("Content-Type: application/json")
    @GET("/ex/api_Sanpham/getobj")
    suspend fun getLikeView(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("loaitk") typeAccount: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Product>>



}