package com.itechpro.data.api

import com.itechpro.domain.model.Evaluate
import com.itechpro.domain.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface ProductAPI {





    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend  fun getInfoProduct(
        @Query("obj") chitietsanpham: String?,
        @Query("mode") modechitietsanpham: String?,
        @Query("idsanpham") idProduct: String?,
        @Query("iddonvi") idUnit: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Product>>
    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend  fun getInfoProductOff(
        @Query("obj") chitietsanpham: String?,
        @Query("mode") modechitietsanpham: String?,
        @Query("idsanpham") idProduct: String?,
        @Query("iddonvi") idUnit: String?,
    ): Response<List<Product>>


    @Headers("Content-Type: application/json")
    @GET("/ex/api_DanhGiaSanPham/getobj")
    suspend  fun getEvaluates(
        @Query("obj") laythongtin: String?,
        @Query("mode") laydsdanhgia: String?,
        @Query("idsanpham") idProduct: String?,
        @Query("soluong") count: String?,
        @Header("Authorization") authen: String?
    ): Response<Evaluate>
    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend  fun getEvaluatesOff(
        @Query("obj") laythongtin: String?,
        @Query("mode") laydsdanhgia: String?,
        @Query("idsanpham") idProduct: String?,
        @Query("soluong") count: String?,

        ): Response<Evaluate>

    //
    @Headers("Content-Type: application/json")
    @GET("/ex/api_Sanpham/getobj")
    suspend  fun getTypeReport(
        @Query("obj") laythongtin: String?,
        @Query("mode") laydsdanhgia: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Product>>

    //http://192.168.1.9:666/ex/api_Sanpham/getobj?idsanpham=11&mode=deletes&iddonvi=2&loaitk=khachhang&obj=laydulieu
    @Headers("Content-Type: application/json")
    @GET("/ex/api_Sanpham/getobj")
    suspend  fun getUnLike(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("loaitk") typeAccount: String?,
        @Query("idsanpham") idProduct: String?,
        @Query("iddonvi") idUnit: String?,

        @Header("Authorization") authen: String?
    ): Response<List<Product>>


    @Headers("Content-Type: application/json")
    @POST
    suspend fun addEditLike(
        @Url url: String,
        @Body body: Product?,
        @Header("Authorization") authen: String?
    ): Response<List<Product>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend   fun getProductsByIdParent(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("idnhomsanpham") idGroup: String?,
        @Query("loai") type: String?,
        @Query("idcha") idParent: String?,
        @Query("tukhoa") searchKey: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Product>>


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend   fun getProductsByIdParentOff(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("idnhomsanpham") idGroup: String?,
        @Query("loai") type: String?,
        @Query("idcha") idParent: String?,
        @Query("tukhoa") searchKey: String?,

        ): Response<List<Product>>
}