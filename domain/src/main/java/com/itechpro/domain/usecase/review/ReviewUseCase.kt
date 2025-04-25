package com.itechpro.domain.usecase.review





import com.itechpro.domain.model.NetworkResponse
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.review.ReviewAttach
import com.itechpro.domain.model.review.ReviewResult

import com.itechpro.domain.repository.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import javax.inject.Inject

class ReviewUseCase @Inject constructor(
    private val repository: ReviewRepository,

    ) {


    fun getReviews(
        offline: Boolean,
        idReview: String,
        count: String,
        authen: String
    ): Flow<NetworkResponse<ReviewResult>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (offline) {
                repository.getReviewsOff(idReview, count)
            } else {
                repository.getReviews(idReview, count, authen)
            }

            when (result) {
                is NetworkResponse.Success -> {
                    val list = result.data.lst_danhgia
                    val ratingScore = result.data.diemdanhgia?:0f
                    val customResult = ReviewResult(
                        totalReview = list.size,
                        reviewList = list,
                        ratingScore = ratingScore
                    )
                    emit(NetworkResponse.Success(customResult))
                }

                is NetworkResponse.Error -> emit(result)
                is NetworkResponse.Loading -> emit(result)
            }
        }.flowOn(Dispatchers.IO)
    }



    fun addEditLike(
        url: String,
        obj: Product,
        authen: String
    ): Flow<NetworkResponse<List<Product>>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val result = repository.addEditLike(url, obj, authen)
                emit(result)
            } catch (e: Exception) {
                emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun uploadReviewFile(

        obj: ReviewAttach,
        authen: String
    ): Flow<NetworkResponse<ResponseBody>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val result = repository.uploadReviewFile("/ex/api_DanhGiaSanPham/adddanhgia", obj, authen)
                emit(result)
            } catch (e: Exception) {
                emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
            }
        }.flowOn(Dispatchers.IO)
    }



}
