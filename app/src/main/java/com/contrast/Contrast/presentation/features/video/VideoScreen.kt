package com.contrast.Contrast.presentation.features.video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.SegmentTab
import com.contrast.Contrast.presentation.components.tab.TabBarRow
import com.contrast.Contrast.presentation.features.video.ui.VideoItem
import com.contrast.Contrast.presentation.features.video.viewModel.VideoViewModel
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.Video

@Composable
fun VideoScreen(
    viewModel: VideoViewModel = hiltViewModel()
) {
    val videos by viewModel.videos.collectAsState()
    val categoryNews by viewModel.categoryNews.collectAsState()
    val domain by viewModel.domain.collectAsState()

    val selectedTab by viewModel.selectedTab.collectAsState()
    val isRefreshing by remember { mutableStateOf(false) }
    var idCategory by remember { mutableStateOf("0") }

    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(selectedTab, categoryNews) {
        if (categoryNews.isNotEmpty() && selectedTab in categoryNews.indices) {
            idCategory =categoryNews[selectedTab].id ?: "0"
            viewModel.getVideos(idCategory)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // 🔍 TopBar tìm kiếm + icon
        TopSearchNotificationCart(
            onSearchClick = { /* TODO: Mở tìm kiếm */ },
            onNotificationClick = { /* TODO: Thông báo */ },
            onCartClick = { /* TODO: Giỏ hàng */ }
        )

        if (isLoading) {
            // Hiển thị loading, ví dụ:
            CustomCircularProgressIndicator()
        }

        TabBarRow(
            tabs = categoryNews,
            color = TealGreen,
            textCorSelect = TealGreen,
            selectedTab = selectedTab,
            onTabSelected = viewModel::onTabSelected
        )

        CustomSwipeRefresh(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.getVideos(idCategory) }
        ) {
        if (videos.isEmpty()) {
            EmptyStateScreen(
                imageRes = R.drawable.nodata,
                size = 60.dp,
                title = stringResource(R.string.no_data)
            )
        } else {
            VideoList(videos)
        }
    }}
}
@Composable
private fun VideoList(videos: List<Video>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(videos) { video ->
            VideoItem(video = video)
        }
    }
}
