package com.itechpro.data.api.oder


import com.itechpro.domain.model.oder.Order

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
interface OderAPI {


    // http://192.168.1.119:224/ex/apiaffiliate/getobj?denngay=2024-07-28&mode=danhsachdonhang&obj=dailyaf&tungay=2024-07-22&xacnhan=1
    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend  fun getOderByConfirm(
        @Query("obj") dailyaf: String?,
        @Query("mode") danhsachdonhang: String?,
        @Query("tungay") startDate: String?,
        @Query("denngay") endDate: String?,
        @Query("xacnhan") confirm: String?,
        @Query("loaitk") typeAccount: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Order>>




    @Headers("Content-Type: application/json")
    @GET("/ex/api/getobj")
    suspend  fun getOderByTypeAccount(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("tungay") startDate: String?,
        @Query("denngay") endDate: String?,
        @Query("idkhachhang") idCustomer:  String?,
        @Query("loaitk") typeAccount:  String?,
        @Header("Authorization") authen: String?
    ): Response<List<Order>>



//http://192.168.1.11:456/ex/apiaffiliate/getobj?ido=5208793555649146107&mode=modedeletedonbanhang&obj=deletedonbanhang

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend   fun deleteOder(
        @Query("obj") deletedonbanhang: String?,
        @Query("mode") modedeletedonbanhang: String?,
        @Query("ido") ids: String?,
        @Query("mamenu") mamenu: String?,
        @Query("os") os: String?,
        @Query("device") device: String?,
        @Query("noidungchinh") noidungchinh: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Order>>



}