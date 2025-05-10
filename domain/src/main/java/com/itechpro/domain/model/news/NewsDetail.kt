package com.itechpro.domain.model.news
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class NewsDetail(
    val title: String,
    val startDate: String,
    val endDate: String,
    val location: String,
    val contentHtml: String, // Nội dung chi tiết dùng HTML để hiển thị trong WebView
    val imageRes: Int // Ảnh nền tiêu đề
)