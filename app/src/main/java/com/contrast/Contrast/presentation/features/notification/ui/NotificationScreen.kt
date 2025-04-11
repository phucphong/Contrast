package com.contrast.Contrast.presentation.features.notification.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.theme.FFD7D7D7
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.features.news.list.NewsItem


import com.itechpro.domain.model.sampleNotifications

//@Composable
//fun NotificationScreen(onBackPress: () -> Unit) {
//    val notifications = remember { sampleNotifications() }
//
//    Scaffold(
//        topBar = {
//            CustomTopAppBarBackTitle(
//                title = stringResource(id = R.string.notification_title),
//                Color.Red,
//                FFD7D7D7,
//                onBackClick = { onBackPress }
//            )
//        }
//
//    ) { paddingValues ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .background(FCFCFC),
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(notifications, key = { it.id }) { notification ->
//                NotificationItem(notification)
//            }
//        }
//    }
//}


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contrast.Contrast.extensions.DateUtils

import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.TabBarRow

import com.contrast.Contrast.presentation.features.news.viewModel.NewsViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.theme.TealGreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.itechpro.domain.model.NetworkResponse

@RequiresApi(Build.VERSION_CODES.O)
@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun NotificationScreen(viewModel: NotificationViewModel = hiltViewModel()) {
    val notifications by viewModel.notifications.collectAsState()

    val domain by viewModel.domain.collectAsState()

    val selectedTab by viewModel.selectedTab.collectAsState()
    val isRefreshing by remember { mutableStateOf(false) }
    var idCategory by remember { mutableStateOf("0") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    var isAlertDialogVisible by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading.collectAsState()



    LaunchedEffect(Unit) {
        startDate = DateUtils.today()
        endDate = DateUtils.today()
    }

    LaunchedEffect(notifications) {
        viewModel.getNotifications(startDate,endDate)
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
                CustomCircularProgressIndicator()
            }
        }

        CustomSwipeRefresh(
            isRefreshing = isRefreshing,
            onRefresh = {  viewModel.getNotifications(startDate,endDate) }
        ) {
            if (notifications.isEmpty()) {
                EmptyStateScreen(
                    imageRes = R.drawable.nodata,
                    size = 60.dp,
                    title = stringResource(R.string.no_data),
                )

            } else {


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()

                        .background(FCFCFC),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notifications, key = { it.id }) { notification ->
                        NotificationItem(notification)
                    }
                }

            }

        }
    }
}
