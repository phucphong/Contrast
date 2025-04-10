package com.itechpro.data.api



import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Customer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface CustomerAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun checkPhone(
        @Query("obj") khachhang: String?,
        @Query("mode") checktrungdienthoai: String?,
        @Query("dienthoai") dienthoai: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Column1>>

    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend fun checkEmail(
        @Query("obj") dailyaf: String?,
        @Query("mode") checkemail: String?,
        @Query("email") email: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Column1>>



    @Headers("Content-Type: application/json")
    @POST
    suspend fun addEditCustomer(
        @Url url: String,
        @Body body: Customer,
        @Header("Authorization") authen: String?,
    ): Response<List<Customer>>

}