package com.itechpro.domain.model.evaluate

data class EvaluateResult(
    val totalEvaluate: Int,
    val ratingScore: Float,
    val evaluateList: List<EvaluateDetail>
)
