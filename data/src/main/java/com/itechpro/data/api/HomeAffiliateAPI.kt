package com.itechpro.data.api
import com.itechpro.domain.model.Category
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.Rotation
import com.itechpro.domain.model.SliderHome
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
interface HomeAffiliateAPI {


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend  fun getSlideHome(
        @Query("obj") sanphamtrangchu: String?,
        @Query("mode") modeslide: String?,
        @Header("Authorization") authen: String?
    ): Response<List<SliderHome>>


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend  fun getSlideHomeOff(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
    ): Response<List<SliderHome>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobj")
    suspend   fun getCategory(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("loai") productType: String?,
        @Query("idcha") idParent: String?,
        @Header("Authorization") authen: String?
    ): Response<List<Category>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend   fun getCategoryOff(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("loai") productType: String?,
        @Query("idcha") idParent: String?,

        ): Response<List<Category>>


    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend   fun getFlashSale(
        @Query("obj") tatcasp: String?,
        @Query("mode") modetatcasp: String?,
        @Header("Authorization") authen: String?
        ): Response<List<Product>>

    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend   fun getFlashSaleOff(
        @Query("obj") tatcasp: String?,
        @Query("mode") modetatcasp: String?,

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

    @Headers("Content-Type: application/json")
    @GET("/ex/apikh/getobj")
    suspend   fun getRotation(
        @Query("obj") obj: String?,
        @Query("mode") mode: String?,
        @Query("loaitk") typeAccount:  String?,

        @Header("Authorization") authen: String?
    ): Response<List<Rotation>>

}