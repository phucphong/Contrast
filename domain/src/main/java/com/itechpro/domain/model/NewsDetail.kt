package com.itechpro.domain.model

data class NewsDetail(
    val title: String,
    val startDate: String,
    val endDate: String,
    val location: String,
    val contentHtml: String, // Nội dung chi tiết dùng HTML để hiển thị trong WebView
    val imageRes: Int // Ảnh nền tiêu đề
)