package com.contrast.Contrast.presentation.features.product_view_save

import com.contrast.Contrast.presentation.features.share.ParticipationResultCard


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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.searchBar.TopTextNotificationCart
import com.contrast.Contrast.presentation.components.searchDialog.SearchConditionDialog
import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabLocal
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.TabBarRowLocal
import com.contrast.Contrast.presentation.components.topAppBar.BackSearchInfomation
import com.contrast.Contrast.presentation.features.affiliate.home.viewModel.HomeAffiliateViewModel
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.ui.ProductRow
import com.contrast.Contrast.presentation.features.product_view_save.viewmodel.ProductViewSaveViewModel
import com.contrast.Contrast.presentation.features.share.viewModel.ShareProductViewModel
import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes
import com.contrast.Contrast.presentation.navigator.routers.CartRoutes
import com.contrast.Contrast.presentation.navigator.routers.NotificationRoutes
import com.contrast.Contrast.presentation.navigator.routers.ProductRoutes
import com.contrast.Contrast.presentation.navigator.routers.ServiceRequestRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductViewSaveScreen(
    navHostController: NavHostController,
    type: String,
    title: String,
    viewModel: ProductViewSaveViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel(),

    ) {
    val state by viewModel.state.collectAsState()
    val promoUiDataMap = viewModel.promoUiDataMap
    val cartState by cartViewModel.state.collectAsState()
    val notificationState by notificationViewModel.state.collectAsState()
    var isInfomation by remember { mutableStateOf(false) }
    var isFinterDialog by remember { mutableStateOf(false) }
    val isRefreshing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    var startDate by remember { mutableStateOf(DateUtils.today()) }
    var endDate by remember { mutableStateOf(DateUtils.today()) }

    var selectedType by remember { mutableStateOf("Ngày") }
    val listState = rememberLazyListState()
    var typeDate by remember { mutableStateOf<DateFieldType>(DateFieldType.END) }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.shareIntentFlow.collectLatest { intent ->
            context.startActivity(intent)
        }
    }

    LaunchedEffect(state.products) {
        viewModel.setInitialProducts(state.products)
    }

    LaunchedEffect(Unit) {
        delay(100)
        viewModel.loadHomeData(forceRefresh = false, type)

        cartViewModel.getCarts(false)
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index to totalItems
        }.distinctUntilChanged().debounce(300).collect { (lastIndex, total) ->
            if (lastIndex != null && total > 0 && lastIndex >= total - 2) {
                Log.d("Paging", "📦 Trigger loadNextPage at index=$lastIndex / total=$total")
                viewModel.loadNextPage()
            }
        }
    }

    LaunchedEffect(state.navEvent) {
        when (val event = state.navEvent) {

            is SplashNaEvent.GoToLogIn -> {
                navHostController.navigate(
                    AuthRoutes.Login.withArgs(
                        isClose = event.isClose,
                    )
                )
                viewModel.resetNavigation()
            }

            is NotificationNavEvent.GoToNotifications -> {
                navHostController.navigate(
                    NotificationRoutes.Notifications.withArgs(
                        startDate = event.startDate, endDate = event.endDate
                    )
                )
                viewModel.resetNavigation()
            }

            is CartNavEvent.GoToCats -> {
                navHostController.navigate(CartRoutes.Carts.route)
                viewModel.resetNavigation()
            }

            is ProductNavEvent.GoToProductsCategory -> {
                navHostController.navigate(
                    ProductRoutes.ProductByCategory.withArgs(
                        categoryId = event.categoryId,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProductNavEvent.GoToProductDetail -> {
                navHostController.navigate(
                    ProductRoutes.ProductDetail.withArgs(
                        id = event.id,
                        idUnit = event.idUnit,
                        introducerId = event.introducerId,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProductNavEvent.GoToAddServiceRequest -> {
                navHostController.navigate(
                    ServiceRequestRoutes.AddServiceRequest.withArgs(
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

    if (isInfomation) {

    }
    if (isFinterDialog) {
        SearchConditionDialog(onDismiss = { isFinterDialog = false },
            startDate = startDate,
            endDate = endDate,
            selectedType = selectedType,
            type = typeDate,
            onSearch = { search ->
                startDate = search.startDate
                endDate = search.endDate
                selectedType = search.selectedType

                isFinterDialog = false
            })

    }

    Column {
        TopTextNotificationCart(painter = painterResource(R.drawable.quaylai),

            placeholder = title,

            totalCartItems = cartState.totalCartItems,
            totalNotificationItems = notificationState.totalNotificationItems,
            onNotificationClick = {

            },
            onCartClick = { viewModel.onItemCarts() },
            onBackStack = { navHostController.popBackStack() })
        CustomSwipeRefresh(isRefreshing = isRefreshing,
            onRefresh = {
                viewModel.loadHomeData(forceRefresh = isRefreshing, type) }) {

            LazyColumn(
                state = listState, modifier = Modifier
                    .fillMaxSize()
                    .background(FAFAFA)
            ) {



                if (state.tabs.size > 1) {
                    stickyHeader {
                        SegmentTabLocal(tabs = state.tabs, selectedTab = state.selectedTab, onTabSelected = {

                            viewModel.onCategorySelected(type,it, state.tabs)
                        })
                    }
                }

                if (state.isLoading) {
                    item {
                        CustomCircularProgressIndicatorDialog(show = isLoading,
                            onDismissRequest = { isLoading = false })
                    }
                }else{
                    if (state.pagedProducts.isEmpty()) {
                        item {
                            EmptyStateScreen(
                                imageRes = R.drawable.emptycart,
                                size = 90.dp,
                                title = stringResource(R.string.empty_product),
                                background = FFFAFAFA,
                                modifier = Modifier.padding(40.dp)
                            )
                        }

                    } else {
                        val rows = state.pagedProducts.chunked(2)
                        items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                            ProductRow(domain = state.domain,
                                token = state.token,
                                pointAffiliate = state.pointAffiliate,
                                rowProducts = row,
                                isShare = false,
                                promoUiDataMap = promoUiDataMap,
                                onItemClick = { viewModel.onItemProductSelected(it) },
                                onClickCart = { cartViewModel.onItemAddCart(it) },
                                onClickAddServiceRequest = {
                                    viewModel.onAddServiceRequestSelected(it)

                                },
                                onClickShare = { obj ->


                                })
                        }
                    }
                }

            }
        }
    }
}
