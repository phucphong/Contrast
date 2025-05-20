package com.itechpro.domain.usecase.review





import android.util.Log
import com.itechpro.domain.enumApp.ReviewFilterType
import com.itechpro.domain.enumApp.ReviewSelectedFilter
import com.itechpro.domain.model.network.NetworkResponse
import com.itechpro.domain.model.review.ReviewAttach
import com.itechpro.domain.model.review.ReviewDetail
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

        idReview: String,
        count: String,
        authen: String
    ): Flow<NetworkResponse<ReviewResult>> {
        return flow {
            emit(NetworkResponse.Loading)

            val result = if (authen.isEmpty()) {
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

    fun filter(reviews: List<ReviewDetail>, filter: ReviewSelectedFilter): List<ReviewDetail> {
        return when (filter) {
            is ReviewSelectedFilter.Type -> {
                when (filter.type) {
                    ReviewFilterType.ALL -> reviews
                    ReviewFilterType.COMMENT_ONLY -> reviews.filter { it.noidung?.isNotBlank() == true }
                    ReviewFilterType.IMAGE_ONLY -> reviews.filter { it.lst_dinhkem.isNotEmpty() }
                    ReviewFilterType.STAR_5 -> reviews.filter { it.diem == 5 }
                    ReviewFilterType.STAR_4 -> reviews.filter { it.diem == 4 }
                    ReviewFilterType.STAR_3 -> reviews.filter { it.diem == 3 }
                    ReviewFilterType.STAR_2 -> reviews.filter { it.diem == 2 }
                    ReviewFilterType.STAR_1 -> reviews.filter { it.diem == 1 }
                }
            }
            is ReviewSelectedFilter.Star -> reviews.filter { it.diem == filter.star }
            ReviewSelectedFilter.None -> emptyList()
        }
    }



    fun uploadReviewFile(

        obj: ReviewAttach,
        authen: String
    ): Flow<NetworkResponse<ResponseBody>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val result = repository.uploadReviewFile("/ex/api_DanhGiaSanPham/adddanhgia", obj, authen)
                Log.d("uploadReviewFile", "response = $result") // 👈 Thêm log
                emit(result)
            } catch (e: Exception) {
                emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
            }
        }.flowOn(Dispatchers.IO)
    }


    fun uploadReview(

        obj: ReviewAttach,
        authen: String
    ): Flow<NetworkResponse<ResponseBody>> {
        return flow {
            emit(NetworkResponse.Loading)
            try {
                val result = repository.uploadReview("/ex/api_DanhGiaSanPham/adddanhgia", obj, authen)
                emit(result)
            } catch (e: Exception) {
                emit(NetworkResponse.Error("Lỗi: ${e.localizedMessage ?: "Không xác định"}"))
            }
        }.flowOn(Dispatchers.IO)
    }



}
