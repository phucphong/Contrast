package com.itechpro.domain.enumApp
sealed class ReviewSelectedFilter {
    object None : ReviewSelectedFilter()
    data class Type(val type: ReviewFilterType) : ReviewSelectedFilter()
    data class Star(val star: Int) : ReviewSelectedFilter()
}
