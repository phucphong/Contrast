package com.itechpro.data.api

import com.itechpro.domain.model.Agency
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface AgencyAPI {

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun getAgencys(
        @Query("obj") dailyaf: String?,
        @Query("mode") laydanhsachdaily: String?,
        @Query("capchau") levelChild: String?,
        @Header("Authorization") authen: String?
    ):Response<List<Agency>>




//    @GET("/posts")
//    suspend fun getPosts(): Response<List<Post>>


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend fun getAgencyId(
        @Query("obj") dailyaf: String?,
        @Query("mode") laychitietdaily: String?,
        @Query("ido") ido: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Agency>>




    @Headers("Content-Type: application/json")
    @POST
    suspend fun addOrEditAgency(
        @Url url: String,
        @Body body: Agency?,
        @Header("Authorization") authen: String?
    ): Response<List<Agency>>

    //   @POST("/ex/apinew/dangkytaikhoan")
//    Call<List<DangkyTk>> dangkytaikhoan(@Body DangkyTk body);
    @Headers("Content-Type: application/json")
    @POST
    suspend fun addOrEditAgencyOff(
        @Url url: String,
        @Body body: Agency?,

        ): Response<List<Agency>>



//    @Headers("Content-Type: application/json")
//    @GET("/ex/apiaffiliate/getobj")
//    suspend fun checkPhone(
//        @Query("obj") khachhang: String?,
//        @Query("mode") checktrungdienthoai: String?,
//        @Query("dienthoai") dienthoai: String?,
//        @Query("idnhanvien") ido: String?,
//        @Header("Authorization") authen: String?
//    ): Response<List<Column1>>
//
//    //https://aff.ezmax.vn/ex/apiaffiliate/getobj?email=hunghn%40itechpro.vn&idnhanvien=1804806&mode=checkemail&obj=dailyaf
//    @Headers("Content-Type: application/json")
//    @GET("/ex/apiaffiliate/getobj")
//    suspend fun checkEmail(
//        @Query("obj") dailyaf: String?,
//        @Query("mode") checkemail: String?,
//        @Query("email") email: String?,
//        @Query("idnhanvien") idnhanvien: String?,
//        @Header("Authorization") authen: String?
//    ): Response<List<Column1>>
//
//    @Headers("Content-Type: application/json")
//    @GET("/ex/apiaffiliate/laydulieu_noAt")
//    suspend fun checkPhoneOff(
//        @Query("obj") khachhang: String?,
//        @Query("mode") checktrungdienthoai: String?,
//        @Query("dienthoai") dienthoai: String?,
//        @Query("nguoigioithieu") referralPerson: String?,
//        @Query("key") key: String?,
//
//        ): Response<List<Column1>>
//
//    @Headers("Content-Type: application/json")
//    @GET("/ex/apiaffiliate/laydulieu_noAt")
//    suspend fun checkEmailOff(
//        @Query("obj") khachhang: String?,
//        @Query("mode") checktrungdienthoai: String?,
//        @Query("email") dienthoai: String?,
//        @Query("nguoigioithieu") referralPerson: String?,
//        @Query("key") key: String?,
//
//        ): Response<List<Column1>>



}