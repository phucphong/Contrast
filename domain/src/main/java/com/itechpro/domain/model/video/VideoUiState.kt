package com.itechpro.domain.model.video

import com.itechpro.domain.model.Video
import com.itechpro.domain.model.category.Category
import com.itechpro.domain.model.navigationEvent.NavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent

data class VideoUiState(
    val videos: List<Video> = emptyList(),
    val pagedVideos: List<Video> = emptyList(),
    val selectedTab: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val obj: Video? = null,
    val categoryNews: List<Category> = emptyList(),
    val categoryNewsHeart: List<Category> = emptyList(),
    val domain: String? = "",

    val navEvent: NavEvent = ProductNavEvent.None
)
