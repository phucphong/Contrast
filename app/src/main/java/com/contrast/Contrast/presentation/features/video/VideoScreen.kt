package com.contrast.Contrast.presentation.features.video

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh

import com.contrast.Contrast.presentation.components.tab.TabBarRow
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.video.ui.VideoItem
import com.contrast.Contrast.presentation.features.video.viewModel.VideoViewModel
import com.contrast.Contrast.presentation.navigator.routers.CartRoutes
import com.contrast.Contrast.presentation.navigator.routers.NotificationRoutes
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
    val notificationState by notificationViewModel.state.collectAsState()
    var isLoading by remember { mutableStateOf(true) }
    val isRefreshing by remember { mutableStateOf(false) }
    var idCategory by remember { mutableStateOf("0") }
    var searchText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.loadUserInfo()
        cartViewModel.getCarts(false)
    }
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//
//            if (event == Lifecycle.Event.ON_RESUME) {
//
//                viewModel.getNews(idCategory)
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
//    }

    LaunchedEffect(state.videos) {
        viewModel.setInitialVideo(state.videos)
    }

    LaunchedEffect(state.selectedTab,state. categoryNews) {
        if (state.categoryNews.isNotEmpty() &&state. selectedTab in state.categoryNews.indices) {
            idCategory =state.categoryNews[state.selectedTab].id ?: "0"
            Log.e("idCategory",idCategory)
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
    Column(modifier = Modifier.fillMaxSize().padding( top = 10.dp ,bottom=80.dp)) {

        // 🔍 TopBar tìm kiếm + icon
        TopSearchNotificationCart(
            isTexField = true,
            text = searchText,
            totalNotificationItems =notificationState. totalNotificationItems,
            totalCartItems = cartState.totalCartItems,
            onTextChanged = { searchText = it
                viewModel.searchLocal (searchText,state.videos)
                            },
            onNotificationClick = { viewModel.onItemNotificationSelected() },
            onCartClick = { viewModel.onItemCarts() },

        )



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


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading) {
                    item {
                        CustomCircularProgressIndicatorDialog(
                            show = isLoading,
                            onDismissRequest = { isLoading = false })
                    }
                }else{
                    items(state.pagedVideos) { video ->
                        VideoItem(video = video)
                    }
                }


            }
        }
    }}
}
