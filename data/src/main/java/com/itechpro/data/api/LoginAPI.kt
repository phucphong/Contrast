package com.itechpro.data.api



import com.itechpro.domain.model.login.Login
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginAPI {
    @Headers("Content-Type: application/json")
    @POST("/ex/api/login")
    suspend fun login(
        @Body body: Login,
    ): Response<Login>



}