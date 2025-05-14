package com.contrast.Contrast.presentation.features.news.list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.TabBarRow
import com.contrast.Contrast.presentation.features.cart.CartViewModel

import com.contrast.Contrast.presentation.features.news.viewModel.NewsViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.navigator.router.routes.CartRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.NewsRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.NotificationRoutes
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NewsNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsScreen(navHostController: NavHostController
               , viewModel: NewsViewModel = hiltViewModel()
               , cartViewModel: CartViewModel = hiltViewModel()
               , notificationViewModel: NotificationViewModel = hiltViewModel()
) {



    val isRefreshing by remember { mutableStateOf(false) }
    var idCategory by remember { mutableStateOf("0") }

    var searchText by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    val cartState by cartViewModel.state.collectAsState()
    val notificationState by notificationViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        cartViewModel.getCarts(false)
    }

    LaunchedEffect(state.selectedTab, state.categoryNews) {
        if (state.categoryNews.isNotEmpty() && state.selectedTab in state.categoryNews.indices) {
            idCategory = state.categoryNews[state.selectedTab].id ?: "0"
            viewModel.getNews(idCategory)
        }
    }
    LaunchedEffect(state.news) {
        viewModel.setInitialNews(state.news)
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
            is NewsNavEvent.GoToNewDetail -> {
                navHostController.navigate(
                    NewsRoutes.NewsDetail.withArgs(
                        id = event.id
                    )
                )
                viewModel.resetNavigation()
            }

            else -> Unit
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_RESUME) {

                viewModel.getNews(idCategory)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

        Column(modifier = Modifier.fillMaxSize()) {
            TopSearchNotificationCart(
                isTexField = true,
                text = searchText,
                totalCartItems = cartState.totalCartItems,
                totalNotificationItems = notificationState.totalNotificationItems,
                onTextChanged = { searchText = it
                    viewModel.searchLocal (searchText,state.news)
                                },
                onNotificationClick = { viewModel.onItemNotificationSelected() },
                onCartClick = { viewModel.onItemCarts() },

                )
            if (state.isLoading) {
                // Hiển thị loading, ví dụ:
                CustomCircularProgressIndicator()
            }

            TabBarRow(
                tabs =state.categoryNews,
                color = TealGreen,
                textCorSelect = TealGreen,
                selectedTab = state.selectedTab,
                type = "",
                onTabSelected = viewModel::onTabSelected
            )

            CustomSwipeRefresh(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.getNews(idCategory) }
            ) {
            if (state.pagedNews.isEmpty()) {
                EmptyStateScreen(
                    imageRes = R.drawable.nodata,
                    size = 60.dp,
                    title = stringResource(R.string.no_data),
                )
            } else {
                LazyColumn {
                    items(state.pagedNews) { article ->
                        state.domain?.let { NewsItem(article, it, onClickNew={
                            viewModel.onItemNewSelected(article)

                        }) }
                    }
                }
            }
        }

    }
}
