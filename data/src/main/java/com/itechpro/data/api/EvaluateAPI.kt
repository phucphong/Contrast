package com.itechpro.data.api



import com.itechpro.domain.model.evaluate.Evaluate
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.product.ProductDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface EvaluateAPI {







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



    @Headers("Content-Type: application/json")
    @POST
    suspend fun addEditLike(
        @Url url: String,
        @Body body: Product?,
        @Header("Authorization") authen: String?
    ): Response<List<Product>>


}