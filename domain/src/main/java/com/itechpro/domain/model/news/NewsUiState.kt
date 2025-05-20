package com.itechpro.domain.model.news



import com.itechpro.domain.model.Video
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent

data class NewsUiState(
    val news: List<News> = emptyList(),
    val pagedNews: List<News> = emptyList(),
    val categoryNews: List<Category> = emptyList(),
    val selectedTab: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val newDetail: News? = null,
    val domain: String? = "",
    val navEvent: NavEvent = ProductNavEvent.None
)
