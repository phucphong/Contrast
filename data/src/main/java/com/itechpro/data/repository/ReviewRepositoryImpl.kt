package com.itechpro.data.repository





import com.itechpro.data.api.ReviewAPI
import com.itechpro.domain.model.review.Review

import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.review.ReviewAttach
import com.itechpro.domain.repository.ReviewRepository

import okhttp3.ResponseBody

import javax.inject.Inject


class ReviewRepositoryImpl @Inject constructor(
    private val api: ReviewAPI
) : ReviewRepository {






    override suspend fun getReviewsOff(idProduct: String,count: String): NetworkResponse<Review> {
        val response = api.getReviewsOff("laythongtin","laydsdanhgia",idProduct,count, )
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error("Cart data is null")
            }
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }

    override suspend fun getReviews(idProduct: String,count: String, authen: String): NetworkResponse<Review> {
        val response = api.getReviews("laythongtin","laydsdanhgia",idProduct,count, authen)
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error("Cart data is null")
            }
        } else {
            NetworkResponse.Error("Lỗi: ${response.message()}")
        }
    }



    override suspend fun addEditLike(url: String, obj: Product, authen: String): NetworkResponse<List<Product>> {
        return try {
            val response = api.addEditLike(url, obj,authen)
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body() ?: emptyList())
            } else {
                NetworkResponse.Error("Lỗi: ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")


        }
    }

    override suspend fun uploadReviewFile(url: String, obj: ReviewAttach, authen: String): NetworkResponse<ResponseBody> {
        return try {

            val response = api.uploadReviewFile(
                file = obj.files,
                description = obj.description,
                ido = obj.ido,
                noidung = obj.noidung,
                idsfilexoa = obj.idsFileXoa,
                rank = obj.diem,
                idProduct = obj.idProduct,
                idUnit = obj.idUnit,
                typeAccount = obj.typeAccount,
                mamenu = obj.mamenu,
                hanhdong = obj.hanhdong,
                device = obj.device,
                os = obj.os,
                authen = authen
            )

            val body = response.body()
            if (body != null) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error("Cart data is null")
            }
        } catch (e: Exception) {
            NetworkResponse.Error("Exception: ${e.message}")
        }
    }


}
