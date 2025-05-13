package com.contrast.Contrast.presentation.features.video

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh

import com.contrast.Contrast.presentation.components.tab.TabBarRow
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.video.ui.VideoItem
import com.contrast.Contrast.presentation.features.video.viewModel.VideoViewModel
import com.contrast.Contrast.presentation.navigator.router.routes.CartRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.NotificationRoutes
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.Video
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VideoScreen(navHostController: NavHostController,
                viewModel: VideoViewModel = hiltViewModel(),
                cartViewModel: CartViewModel = hiltViewModel(),
                notificationViewModel: NotificationViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()
    val cartState by cartViewModel.state.collectAsState()
    val totalNotificationItems by notificationViewModel.totalNotificationItems.collectAsState()
    val isRefreshing by remember { mutableStateOf(false) }
    var idCategory by remember { mutableStateOf("0") }
    var searchText by remember { mutableStateOf("") }


    LaunchedEffect(state.videos) {
        viewModel.setInitialVideo(state.videos)
    }

    LaunchedEffect(state.selectedTab,state. categoryNews) {
        if (state.categoryNews.isNotEmpty() &&state. selectedTab in state.categoryNews.indices) {
            idCategory =state.categoryNews[state.selectedTab].id ?: "0"
            viewModel.getVideos(idCategory)
        }
    }
    LaunchedEffect(state.navEvent) {
        when (val event = state.navEvent) {
            is NotificationNavEvent.GoToNotifications -> {
                navHostController.navigate(
                    NotificationRoutes.Notifications.withArgs(
                        startDate = event.startDate,
                        endDate = event.endDate
                    )
                )
                viewModel.resetNavigation()
            }
            is CartNavEvent.GoToCats -> {
                navHostController.navigate(CartRoutes.Carts.route)
                viewModel.resetNavigation()
            }




            else -> Unit
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {

        // 🔍 TopBar tìm kiếm + icon
        TopSearchNotificationCart(
            isTexField = true,
            text = searchText,

            totalNotificationItems = totalNotificationItems,
            totalCartItems = cartState.totalCartItems,
            onTextChanged = { searchText = it
                viewModel.searchLocal (searchText,state.videos)

                            },
            onNotificationClick = { viewModel.onItemNotificationSelected() },
            onCartClick = { viewModel.onItemCarts() },

        )

        if (state.isLoading) {
            // Hiển thị loading, ví dụ:
            CustomCircularProgressIndicator()
        }

        TabBarRow(
            tabs = state.categoryNews,
            color = TealGreen,
            textCorSelect = TealGreen,
            selectedTab = state.selectedTab,
            type = "",
            onTabSelected = {
                searchText = ""
                viewModel.onTabSelected(it)

            }
        )

        CustomSwipeRefresh(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.getVideos(idCategory) }
        ) {
        if (state.pagedVideos.isEmpty()) {
            EmptyStateScreen(
                imageRes = R.drawable.nodata,
                size = 60.dp,
                title = stringResource(R.string.no_data)
            )
        } else {
            VideoList(state.pagedVideos)
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
