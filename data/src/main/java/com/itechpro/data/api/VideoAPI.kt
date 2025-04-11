package com.itechpro.data.api





import com.itechpro.domain.model.Category
import com.itechpro.domain.model.Login
import com.itechpro.domain.model.Video
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface VideoAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
//    @GET("/ex/apiaffiliate/getobjpublic") off
    suspend  fun getVideos(

        @Query("obj") tintuc: String,
        @Query("mode") modedstintuc: String,
        @Query("idchudetintuc") idCategory: String,
        @Header("Authorization") authen: String
    ): Response<List<Video>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend  fun getVideosOff(
        @Query("obj") tintuc: String?,
        @Query("mode") modedstintuc: String?,
        @Query("idchudetintuc") idCategory: String?
    ): Response<List<Video>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend  fun getCategory(
        @Query("obj") tintuc: String?,
        @Query("mode") modedschudetintuc: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>
   
    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend  fun getCategoryOff(
        @Query("obj") tintuc: String?,
        @Query("mode") modedschudetintuc: String?
    ): Response<List<Category>>



}