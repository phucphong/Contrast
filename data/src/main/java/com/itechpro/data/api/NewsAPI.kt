package com.itechpro.data.api



import com.itechpro.domain.model.Category
import com.itechpro.domain.model.news.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
//    @GET("/ex/apiaffiliate/getobjpublic") off
    suspend  fun getNews(

        @Query("obj") tintuc: String,
        @Query("mode") modedstintuc: String,
        @Query("idchudetintuc") idCategory: String,
        @Header("Authorization") authen: String
    ): Response<List<News>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend  fun getNewsOff(
        @Query("obj") tintuc: String?,
        @Query("mode") modedstintuc: String?,
        @Query("idchudetintuc") idCategory: String?
    ): Response<List<News>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend  fun getCategory(
        @Query("obj") tintuc: String?,
        @Query("mode") modedschudetintuc: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>
    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend  fun getNewDetail(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<News>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend  fun getCategoryOff(
        @Query("obj") tintuc: String?,
        @Query("mode") modedschudetintuc: String?
    ): Response<List<Category>>



}