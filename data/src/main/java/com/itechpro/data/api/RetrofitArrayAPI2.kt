package com.itechpro.data.api




import com.itechpro.domain.model.Setting
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface RetrofitArrayAPI2 {


    @Headers("Content-Type: application/json")
    @GET("/ex/apinew/getDomain")
    fun getVerificationCodesOnITP(
        @Query("key") key: String?,
        @Query("maxt") maxt: String?
    ): Call<List<Setting>>



    // check tạm dừng hoạt động
    @Headers("Content-Type: application/json")
    @GET("/ex/apinew/chektamdung")
    fun checkCustomerActiveContract(
        @Query("key") key: String?,
        @Query("khachhangname") khachhangname: String?
    ): Call<List<Setting>>


}