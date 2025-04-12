package com.itechpro.data.api





import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.FireBaseClient
import com.itechpro.domain.model.Login
import com.itechpro.domain.model.News
import com.itechpro.domain.model.Notification
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

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