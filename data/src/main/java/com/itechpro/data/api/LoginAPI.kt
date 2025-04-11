package com.itechpro.data.api



import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Login
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface LoginAPI {
    @Headers("Content-Type: application/json")
    @POST("/ex/api/login")
    suspend fun login(
        @Body body: Login,
    ): Response<List<Login>>



}