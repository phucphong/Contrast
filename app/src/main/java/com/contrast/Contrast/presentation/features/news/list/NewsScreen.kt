package com.contrast.Contrast.presentation.features.news.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.TabBarRow

import com.contrast.Contrast.presentation.features.news.viewModel.NewsViewModel
import com.contrast.Contrast.presentation.theme.TealGreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.itechpro.domain.model.NetworkResponse

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
    val news by viewModel.news.collectAsState()
    val categoryNews by viewModel.categoryNews.collectAsState()
    val domain by viewModel.domain.collectAsState()

    val selectedTab by viewModel.selectedTab.collectAsState()
    val isRefreshing by remember { mutableStateOf(false) }
    var idCategory by remember { mutableStateOf("0") }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    var isAlertDialogVisible by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading.collectAsState()



    LaunchedEffect(selectedTab, categoryNews) {
        if (categoryNews.isNotEmpty() && selectedTab in categoryNews.indices) {
            idCategory =categoryNews[selectedTab].id ?: "0"
            viewModel.getNews(idCategory)
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        TopSearchNotificationCart(
            onSearchClick = { /* mở màn tìm kiếm */ },
            onNotificationClick = { /* mở thông báo */ },
            onCartClick = { /* mở giỏ hàng */ }
        )
        if (isLoading) {
            // Hiển thị loading, ví dụ:
            Box(
                modifier = Modifier.fillMaxSize().padding(top = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Hiển thị danh sách như cũ
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
            onRefresh = { viewModel.getNews(idCategory) }
        ) {
            if (news.isEmpty()) {
                EmptyStateScreen(
                    imageRes = R.drawable.nodata,
                    size = 60.dp,
                    title = stringResource(R.string.no_data),
                )

            } else {
                LazyColumn {
                    items(news) { article ->
                        domain?.let { NewsItem(article, it) }
                    }
                }
            }
        }

    }
}
