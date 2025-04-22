package com.contrast.Contrast.presentation.features.affiliate.home


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.slider.ImageSliderFromUrl
import com.contrast.Contrast.presentation.components.tab.TabBarPagedGridScrollable
import com.contrast.Contrast.presentation.components.tab.TabBarRowLocal
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.ui.ProductCardAffiliate
import com.contrast.Contrast.presentation.features.product.ui.ProductRow
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFD9D9D9
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(
    navHostController: NavHostController,
    viewModel: HomeAffiliateViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val slides by viewModel.slides.collectAsState()
    val categorys by viewModel.categorys.collectAsState()
    val tabs by viewModel.tabs.collectAsState()
    val products by viewModel.products.collectAsState()
    val pagedProducts by viewModel.pagedProducts.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val promoUiDataMap = viewModel.promoUiDataMap
    val totalCartItems by cartViewModel.totalCartItems.collectAsState()
    val totalNotificationItems by notificationViewModel.totalNotificationItems.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }

    val navEvent by viewModel.navigationEvent.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(products) { viewModel.setInitialProducts(products) }

    LaunchedEffect(Unit) {
        delay(300) // cho hệ thống khởi động mạng nếu vừa chuyển 4G
        viewModel.loadHomeData()
    }


    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { index ->
                val total = listState.layoutInfo.totalItemsCount
                if (index != null && index >= total - 2) {
                    viewModel.loadNextPage()
                }
            }
    }

    LaunchedEffect(navEvent) {
        when (val event = navEvent) {
            is ProductNavEvent.GoToProductsCategory -> {
                navHostController.navigate(NavRoutes.ProductByCategory.createRoute(event.categoryId))
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToProductDetail -> {
                navHostController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("id", event.id)
                    set("idUnit", event.idUnit)
                }
                navHostController.navigate(NavRoutes.ProductDetail.route)
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToAddServiceRequest -> {
                navHostController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("id", event.id)
                    set("serviceName", event.serviceName)
                    set("idUnit", event.idUnit)
                    set("discount", event.discount)
                }
                navHostController.navigate(NavRoutes.AddServiceRequest.route)
                viewModel.resetNavigation()
            }
            is NotificationNavEvent.GoToNotifications -> {
                navHostController.currentBackStackEntry?.savedStateHandle?.apply {
                    set("startDate", event.startDate)
                    set("endDate", event.endDate)
                }
                navHostController.navigate(NavRoutes.Notifications.route)
                viewModel.resetNavigation()
            }
            else -> Unit
        }
    }



    Column(modifier = Modifier.fillMaxSize()) {
        TopSearchNotificationCart(
            isTexField = true,
            text = searchText,
            totalNotificationItems = totalNotificationItems,
            totalCartItems = totalCartItems,
            onTextChanged = { searchText = it },
            onSearchClick = { },
            onNotificationClick = { viewModel.onItemNotificationSelected() },
            onCartClick = { }
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(FAFAFA),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                if (slides.isNotEmpty()) {
                    ImageSliderFromUrl(
                        domain = domain,
                        autoScroll = true,
                        slides = slides,
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                if (categorys.isNotEmpty()) {
                    TabBarPagedGridScrollable(
                        tabs = categorys,
                        selectedTab = selectedCategory,
                        domain = domain,
                        type = "name",
                        onTabSelected = { index ->
                            selectedCategory = index
                            viewModel.onTabSelected(index, categorys[index])
                        }
                    )
                }
            }

            item {
                CustomDividerColor(color = FFD9D9D9, padding = 5.dp)
            }

            if (tabs.size > 1) {
                stickyHeader {
                    TabBarRowLocal(
                        tabs = tabs,
                        selectedTab = selectedTab,
                        onTabSelected = {
                            viewModel.onCategorySelected(it, tabs)
                        }
                    )
                }
            }

            val rows = products.chunked(2)
            items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                ProductRow(
                    domain = domain,
                    rowProducts = row,
                    promoUiDataMap = promoUiDataMap,
                    onItemClick = { viewModel.onItemProductSelected(it) },
                    onClickCart = { viewModel.onItemCart(it) },
                    onClickAddServiceRequest = { viewModel.onAddServiceRequestSelected(it) }
                )
            }
        }
    }
}