package com.itechpro.data.api



import com.itechpro.domain.model.FileUpload
import com.itechpro.domain.model.review.Review
import com.itechpro.domain.model.Product
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

interface ReviewAPI {







    @Headers("Content-Type: application/json")
    @GET("/ex/api_DanhGiaSanPham/getobj")
    suspend  fun getReviews(
        @Query("obj") laythongtin: String?,
        @Query("mode") laydsdanhgia: String?,
        @Query("idsanpham") idProduct: String?,
        @Query("soluong") count: String?,
        @Header("Authorization") authen: String?
    ): Response<Review>
    @Headers("Content-Type: application/json")
    @GET("/ex/apiaffiliate/getobjpublic")
    suspend  fun getReviewsOff(
        @Query("obj") laythongtin: String?,
        @Query("mode") laydsdanhgia: String?,
        @Query("idsanpham") idProduct: String?,
        @Query("soluong") count: String?,

        ): Response<Review>



    @Headers("Content-Type: application/json")
    @POST
    suspend fun addEditLike(
        @Url url: String,
        @Body body: Product?,
        @Header("Authorization") authen: String?
    ): Response<List<Product>>


    @Headers("Content-Type: application/json")
    @POST("/ex/api_DanhGiaSanPham/adddanhgia")
    suspend   fun uploadReview(
        @Query("ido") ido: String?,
        @Query("noidung") noidung: String?,
        @Query("idsfilexoa") idsfilexoa: String?,
        @Query("diem") rank: String?,
        @Query("idsanpham") idProduct: String?,
        @Query("iddonvi") idUnit: String?,
        @Query("loaitk") typeAccount: String?,
        @Query("mamenu") mamenu: String?,
        @Query("hanhdong") hanhdong: String?,
        @Query("device") device: String?,
        @Query("os") os: String?,
        @Header("Authorization") authen: String?
    ): Response<ResponseBody>
    //http://192.168.1.31:777/ex/api_DanhGiaSanPham/adddanhgia?diem=5&ido=0&idsanpham=86&idsfilexoa=&noidung=Li%C3%AAm
    @Multipart
    @POST("/ex/api_DanhGiaSanPham/adddanhgia")
    suspend fun uploadReviewFile(
        @Part file: List<FileUpload>?,
        @Query("description") description: String?,
        @Query("ido") ido: String?,
        @Query("noidung") noidung: String?,
        @Query("idsfilexoa") idsfilexoa: String?,
        @Query("diem") rank: String?,
        @Query("idsanpham") idProduct: String?,
        @Query("iddonvi") idUnit: String?,
        @Query("loaitk") typeAccount: String?,
        @Query("mamenu") mamenu: String?,
        @Query("hanhdong") hanhdong: String?,
        @Query("device") device: String?,
        @Query("os") os: String?,
        @Header("Authorization") authen: String?
    ): Response<ResponseBody>



}