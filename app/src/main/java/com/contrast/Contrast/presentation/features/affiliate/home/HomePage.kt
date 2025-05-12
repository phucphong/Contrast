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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.slider.ImageSliderFromUrl
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.TabBarPagedGridScrollable
import com.contrast.Contrast.presentation.components.tab.TabBarRowLocal
import com.contrast.Contrast.presentation.features.affiliate.home.viewModel.HomeAffiliateViewModel
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.flashSale.FlashSaleHome
import com.contrast.Contrast.presentation.features.flashSale.ui.FlashSaleHeader
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.detail.callApi
import com.contrast.Contrast.presentation.features.product.ui.ProductRow
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFD9D9D9
import com.contrast.Contrast.utils.NetworkMonitor
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(
    navHostController: NavHostController,
    viewModel: HomeAffiliateViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val promoUiDataMap = viewModel.promoUiDataMap
    val cartState by cartViewModel.state.collectAsState()
    val totalNotificationItems by notificationViewModel.totalNotificationItems.collectAsState()

    val isRefreshing by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()

    LaunchedEffect(state.products) {
        viewModel.setInitialProducts(state.products)
    }

    LaunchedEffect(Unit) {
        delay(100)
        viewModel.loadHomeData()
        cartViewModel.getCarts(false)
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index to totalItems
        }
            .distinctUntilChanged()
            .debounce(300)
            .collect { (lastIndex, total) ->
                if (lastIndex != null && total > 0 && lastIndex >= total - 2) {
                    Log.d("Paging", "📦 Trigger loadNextPage at index=$lastIndex / total=$total")
                    viewModel.loadNextPage()
                }
            }
    }

    LaunchedEffect(state.navEvent) {
        when (val event = state.navEvent) {
            is NotificationNavEvent.GoToNotifications -> {
                navHostController.navigate(
                    NavRoutes.Notifications.withArgs(
                        startDate = event.startDate,
                        endDate = event.endDate
                    )
                )
                viewModel.resetNavigation()
            }
            is CartNavEvent.GoToCats -> {
                navHostController.navigate(NavRoutes.Carts.route)
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToProductsCategory -> {
                navHostController.navigate(
                    NavRoutes.ProductByCategory.withArgs(
                        categoryId = event.categoryId,
                    )
                )
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToProductDetail -> {
                navHostController.navigate(
                    NavRoutes.ProductDetail.withArgs(
                        id = event.id,
                        idUnit = event.idUnit,
                        introducerId = event.introducerId,
                    )
                )
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToAddServiceRequest -> {
                navHostController.navigate(
                    NavRoutes.AddServiceRequest.withArgs(
                        id = event.id,
                        serviceName = event.serviceName,
                        idUnit = event.idUnit,
                        discount = event.discount
                    )
                )
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
            totalCartItems = cartState.totalCartItems,
            onTextChanged = { searchText = it },
            onSearchClick = { },
            onNotificationClick = { viewModel.onItemNotificationSelected() },
            onCartClick = { viewModel.onItemCarts() }
        )

        CustomSwipeRefresh(isRefreshing = isRefreshing,
            onRefresh = { viewModel.loadHomeData(forceRefresh = true) }) {

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(FAFAFA),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    if (state.slides.isNotEmpty()) {
                        ImageSliderFromUrl(
                            domain = state.domain,
                            autoScroll = true,
                            slides = state.slides,
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }

                item {
                    if (state.categorys.isNotEmpty()) {
                        TabBarPagedGridScrollable(
                            tabs = state.categorys,
                            selectedTab = selectedCategory,
                            domain = state.domain,
                            type = "name",
                            onTabSelected = { index ->
                                selectedCategory = index
                                viewModel.onTabSelected(index, state.categorys[index])
                            }
                        )
                    }
                }

                if (state.flashSales.size > 1) {
                    stickyHeader {
                        FlashSaleHeader()
                    }
                }

                item {
                    if (state.flashSales.isNotEmpty()) {
                        FlashSaleHome(
                            flashSales = state.flashSales,
                            promoUiDataMap = promoUiDataMap,
                            domain = state.domain,
                            onItemProductSelected = { index ->
                                viewModel.onItemProductSelected(index)
                            },
                            onSeeAllClicked = {}
                        )
                    }
                }

                item {
                    CustomDividerColor(color = FFD9D9D9, padding = 5.dp)
                }

                if (state.tabs.size > 1) {
                    stickyHeader {
                        TabBarRowLocal(
                            tabs = state.tabs,
                            selectedTab = state.selectedTab,
                            onTabSelected = {
                                viewModel.onCategorySelected(it, state.tabs)
                            }
                        )
                    }
                }

                val rows = state.pagedProducts.chunked(2)
                Log.e("pagedProducts", state.pagedProducts.size.toString())

                items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                    ProductRow(
                        domain = state.domain,
                        token = state.token,
                        pointAffiliate = state.pointAffiliate,
                        rowProducts = row,
                        promoUiDataMap = promoUiDataMap,
                        onItemClick = { viewModel.onItemProductSelected(it) },
                        onClickCart = { cartViewModel.onItemAddCart(it) },
                        onClickAddServiceRequest = {
                            viewModel.onAddServiceRequestSelected(it)
                        },
                        onClickShare = {}
                    )
                }
            }
        }
    }
}
