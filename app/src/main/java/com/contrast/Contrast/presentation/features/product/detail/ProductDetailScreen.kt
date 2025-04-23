package com.contrast.Contrast.presentation.features.product.detail




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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.button.CustomButton
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.slider.ImageSliderFromUrl
import com.contrast.Contrast.presentation.components.tab.TabBarPagedGridScrollable
import com.contrast.Contrast.presentation.components.tab.TabBarRowLocal
import com.contrast.Contrast.presentation.features.affiliate.home.viewModel.HomeAffiliateViewModel
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.flashSale.FlashSaleHome
import com.contrast.Contrast.presentation.features.flashSale.ui.FlashSaleHeader
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.detail.ui.ProductDetailHeader
import com.contrast.Contrast.presentation.features.product.detail.ui.ProductPriceDetailSection
import com.contrast.Contrast.presentation.features.product.detail.ui.WebViewProduct
import com.contrast.Contrast.presentation.features.product.ui.ProductRow
import com.contrast.Contrast.presentation.features.rating.RatingHeader
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFAFAFAF
import com.contrast.Contrast.presentation.theme.FFD9D9D9
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.utils.NetworkMonitor
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductDetailScreen(
    navHostController: NavHostController,
    idProduct: String,
    idUnit: String,
    viewModel: HomeAffiliateViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val slides by viewModel.slides.collectAsState()
    val categorys by viewModel.categorys.collectAsState()
    val flashSales by viewModel.flashSales.collectAsState()
    val tabs by viewModel.tabs.collectAsState()
    val products by viewModel.products.collectAsState()
    val pagedProducts by viewModel.pagedProducts.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val promoUiDataMap = viewModel.promoUiDataMap
    val totalCartItems by cartViewModel.totalCartItems.collectAsState()
    val totalNotificationItems by notificationViewModel.totalNotificationItems.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()
    val isOnline by NetworkMonitor.isOnline.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }

    val navEvent by viewModel.navigationEvent.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(products) { viewModel.setInitialProducts(products) }

    LaunchedEffect(Unit) {
        delay(100) // cho hệ thống khởi động mạng nếu vừa chuyển 4G
        viewModel.loadHomeData()
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index to totalItems
        }
            .distinctUntilChanged()
            .debounce(300) // ✅ ngăn spam trigger khi scroll nhanh
            .collect { (lastIndex, total) ->
                if (lastIndex != null && total > 0 && lastIndex >= total - 2) {
                    Log.d("Paging", "📦 Trigger loadNextPage at index=$lastIndex / total=$total")
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
            painter=painterResource(R.drawable.quaylai),
            isTexField = false,
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
                .fillMaxSize().weight(1f)
                .background(FAFAFA),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                if (slides.isNotEmpty()) {
                    ImageSliderFromUrl(
                        domain = domain,
                        autoScroll = false,
                        slides = slides,
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                if (flashSales.isNotEmpty()) {
                    ProductPriceDetailSection (
                        productName = "Kem trắng da J&D",
                        price = "₫1.020.000",
                        oldPrice = "₫1.020.000",
                        discountPercent = "-0%",
                        isFlashSale = true,
                        remainingTime = "40:00:40:18",
                        quantity = 1,
                        onQuantityChange = { /* logic */ }
                    )

                }
            }




            if (tabs.size > 1) {
                stickyHeader {
                    RatingHeader(
                        rating = 5.0f,
                        totalReviews = 1,
                        onViewAllClick = { /* TODO: handle click */ }
                    )

                }
            }


            item {
                if (flashSales.isNotEmpty()) {
                    FlashSaleHome(
                        flashSales = flashSales,
                        promoUiDataMap = promoUiDataMap,
                        domain = domain,
                        onItemProductSelected = { index ->
                            viewModel.onItemProductSelected(index)
                        },
                        onSeeAllClicked = {

                        }
                    )
                }
            }


            if (flashSales.size > 1) {
                stickyHeader {
                    ProductDetailHeader(
                        onSaveClick = { },
                        onReportClick = { }
                    )

                }
            }
            item {
                if (flashSales.isNotEmpty()) {
                    WebViewProduct(
                        htmlContent = "",

                        )
                }
            }
            val rows = pagedProducts.chunked(2)
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

        CustomButton(
            text = stringResource(id = R.string.add_to_cart),
            textColor = Color.White,
            containerColor = TealGreen,
            paddingStart = 5.dp,
            paddingTop = 20.dp,
            paddingEnd = 5.dp,
            paddingBottom = 5.dp,
            roundedCornerShape = 10.dp,
            onClick = {}
        )
    }
}