package com.contrast.Contrast.presentation.features.video

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
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
    val selectedTab by viewModel.selectedTab.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        // 🔍 TopBar tìm kiếm + icon
        TopSearchNotificationCart(
            onSearchClick = { /* TODO: Mở tìm kiếm */ },
            onNotificationClick = { /* TODO: Thông báo */ },
            onCartClick = { /* TODO: Giỏ hàng */ }
        )

//        // 🧭 Tabs lựa chọn
//        val tabs = listOf(
//            "Tab1",
//            "Tab2"
//        )
//        TabBarRow(
//            tabs = tabs,
//            color = TealGreen,
//            textCorSelect = TealGreen,
//            selectedTab = selectedTab,
//            onTabSelected = viewModel::onTabSelected
//        )

        // 📹 Nội dung video
        if (videos.isEmpty()) {
            EmptyStateScreen(
                imageRes = R.drawable.nodata,
                size = 60.dp,
                title = stringResource(R.string.no_data)
            )
        } else {
            VideoList(videos)
        }
    }
}
@Composable
private fun VideoList(videos: List<Video>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(videos) { video ->
            VideoItem(video = video)
        }
    }
}
