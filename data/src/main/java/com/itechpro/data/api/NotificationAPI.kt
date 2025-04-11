package com.itechpro.data.api



import com.itechpro.domain.model.Category
import com.itechpro.domain.model.Login
import com.itechpro.domain.model.News
import com.itechpro.domain.model.Notification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface NotificationAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
//    @GET("/ex/apiaffiliate/getobjpublic") off
    suspend  fun getNotifications(

        @Query("obj") tintuc: String,
        @Query("mode") modedstintuc: String,
        @Query("tungay") startDate: String,
        @Query("denngay") endDate: String,
        @Header("Authorization") authen: String
    ): Response<List<Notification>>




    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun getNotificationDetail(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Notification>>




}