package com.itechpro.domain.model.review


sealed class ReviewFilter {
    data object All : ReviewFilter()
    data object CommentOnly : ReviewFilter()
    data object ImageOnly : ReviewFilter()
    data class Star(val star: Int) : ReviewFilter()
}
