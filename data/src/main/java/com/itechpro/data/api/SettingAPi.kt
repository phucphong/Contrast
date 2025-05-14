package com.itechpro.data.api



import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Setting
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface SettingAPi {

    @Headers("Content-Type: application/json")
    @GET("/ex/api/testketnoibrm")
    suspend fun getVerificationCodes(): Response<List<Setting>>

    @GET("/ex/api/Api_GetCommonSettingByName_KDN")
    suspend   fun getAppType(@Query("name") name: String?,
    ): Response<List<Setting>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend fun getSettingViewOff(@Query("obj") obj: String?, @Query("mode") mode: String?): Response<Setting>


    @Headers("Content-Type: application/json")
    @GET("/ex/apinew/getDomain")
    fun getVerificationCodesOnITP(
        @Query("key") key: String?,
        @Query("maxt") maxt: String?
    ): Response<List<Setting>>


}