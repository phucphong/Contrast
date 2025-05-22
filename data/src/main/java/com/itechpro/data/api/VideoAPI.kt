package com.itechpro.data.api





import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.Video
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface VideoAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/apikh/getobj")
    suspend  fun getVideos(
        @Query("obj") tintuc: String,
        @Query("mode") modedstintuc: String,
        @Query("ido") ido: String,
        @Header("Authorization") authen: String
    ): Response<List<Video>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend  fun getVideosOff(
        @Query("obj") tintuc: String?,
        @Query("mode") modedstintuc: String?,
        @Query("ido") idCategory: String?
    ): Response<List<Video>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apikh/getobj")
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

    @Headers("Content-Type: application/json")
    @GET("/ex/apikh/getobj")
    suspend  fun deleteVideoHeart(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("ido") ido: String?,
        @Query("ma") ma: String?,
        @Query("mamenu") mamenu: String?,
        @Query("os") os: String?,
        @Query("device") device: String?,
        @Query("noidungchinh") noidungchinh: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Video>>

    @Headers("Content-Type: application/json")
    @POST("/ex/apiaffiliate/adddaily_Dangky")
    suspend fun addVideoHeart(
        @Body body: Video?,
        ): Response<List<Video>>

}