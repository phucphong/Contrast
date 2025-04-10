package com.itechpro.data.api



import com.itechpro.domain.model.Account
import com.itechpro.domain.model.Column1
import com.itechpro.domain.model.Login
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface LoginAPI {

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun checkPhone(
        @Query("obj") khachhang: String?,
        @Query("mode") checktrungdienthoai: String?,
        @Query("dienthoai") dienthoai: String?,
        @Query("idnhanvien") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Column1>>

    //https://aff.ezmax.vn/ex/apiaffiliate/getobj?email=hunghn%40itechpro.vn&idnhanvien=1804806&mode=checkemail&obj=dailyaf
    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun checkEmail(
        @Query("obj") dailyaf: String?,
        @Query("mode") checkemail: String?,
        @Query("email") email: String?,
        @Query("idnhanvien") idnhanvien: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Column1>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/laydulieu_noAt")
    suspend fun checkPhoneOff(
        @Query("obj") khachhang: String?,
        @Query("mode") checktrungdienthoai: String?,
        @Query("dienthoai") dienthoai: String?,
        @Query("nguoigioithieu") referralPerson: String?,
        @Query("key") key: String?,

        ): Response<List<Column1>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/laydulieu_noAt")
    suspend fun checkEmailOff(
        @Query("obj") khachhang: String?,
        @Query("mode") checktrungdienthoai: String?,
        @Query("email") dienthoai: String?,
        @Query("nguoigioithieu") referralPerson: String?,
        @Query("key") key: String?,

        ): Response<List<Column1>>

    @Headers("Content-Type: application/json")
    @POST("/ex/api/login")
    suspend fun login(

        @Body body: Login,
    ): Response<List<Login>>



}