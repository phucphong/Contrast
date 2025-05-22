package com.itechpro.data.api


import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.home.SliderHome
import com.itechpro.domain.model.income.Income
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
interface ShareProductInComeAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend   fun getActualCommissionByIdOder(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("tungay") startDate: String?,
        @Query("denngay") endDate: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Income>>



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
    @GET("/ex/apiaffiliate/getobj")
    suspend   fun getReportShareLink(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("tungay") startDate: String?,
        @Query("denngay") endDate: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Product>>






}