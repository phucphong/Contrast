package com.itechpro.data.api



import com.itechpro.domain.model.Account
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface AccountAPI {

    @Headers("Content-Type: application/json")
    @POST("/ex/apikh/capnhattaikhoan")
    suspend fun getUpdateAccount(
        @Body body: Account?,
        @Header("Authorization") authen: String?
    ): Response<List<Account>>

    @Headers("Content-Type: application/json")
    @POST("/ex/apiaffiliate/doimatkhau_noAuth")
    suspend fun getUpdateAccountOff(
        @Query("username") username: String?,
        @Query("password") password: String?,
        @Query("key") key: String?,

        ): Response<List<Account>>


}