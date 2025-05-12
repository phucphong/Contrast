package com.itechpro.data.api





import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.product.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface CategoryAffiliateAPI {



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