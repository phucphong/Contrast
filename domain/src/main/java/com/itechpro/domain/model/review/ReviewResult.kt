package com.itechpro.domain.model.review

data class ReviewResult(
    val totalReview: Int,
    val ratingScore: Float,
    val reviewList: List<ReviewDetail>
)
