package com.itechpro.domain.repository




import com.itechpro.domain.model.review.Review
import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.review.ReviewAttach
import okhttp3.ResponseBody


interface ReviewRepository {




    suspend fun getReviews(idProduct: String,count: String,authen: String
    ): NetworkResponse<Review>

    suspend fun getReviewsOff(idProduct: String,count: String,
    ): NetworkResponse<Review>





    suspend fun uploadReviewFile(url: String, obj: ReviewAttach, authen: String): NetworkResponse<ResponseBody>
    suspend fun uploadReview(url: String, obj: ReviewAttach, authen: String): NetworkResponse<ResponseBody>


}
