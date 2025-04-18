package com.itechpro.data.api





import com.itechpro.domain.model.Account

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface OTPAPI {



    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/laydulieu_noAt")
    suspend  fun getPhoneByAccount(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("username") username: String?,
        @Query("key") key: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Account>>




}