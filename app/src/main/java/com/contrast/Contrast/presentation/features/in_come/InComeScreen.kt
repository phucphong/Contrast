package com.contrast.Contrast.presentation.features.in_come




import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.searchDialog.SearchConditionDialog
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.TabBarRowLocal
import com.contrast.Contrast.presentation.components.topAppBar.BackSearchInfomation
import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.features.affiliate.home.viewModel.HomeAffiliateViewModel
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.ui.ProductRow
import com.contrast.Contrast.presentation.features.share.viewModel.ShareProductViewModel
import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes
import com.contrast.Contrast.presentation.navigator.routers.CartRoutes
import com.contrast.Contrast.presentation.navigator.routers.NotificationRoutes
import com.contrast.Contrast.presentation.navigator.routers.ProductRoutes
import com.contrast.Contrast.presentation.navigator.routers.ServiceRequestRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.contrast.Contrast.presentation.theme.FFFF5722
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InComeScreen(
    navHostController: NavHostController,
    title: String,
    viewModel: ShareProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),


    ) {
    val state by viewModel.state.collectAsState()

    var isLoading by remember { mutableStateOf(true) }
    var isInfomation by remember { mutableStateOf(false) }
    var isFinterDialog by remember { mutableStateOf(false) }
    val isRefreshing by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(DateUtils.today()) }
    var endDate by remember { mutableStateOf(DateUtils.today()) }
    var status by remember { mutableStateOf("all") }
    var selectedType by remember { mutableStateOf("Ngày") }
    val listState = rememberLazyListState()
    var type by remember { mutableStateOf<DateFieldType>(DateFieldType.END) }
    LaunchedEffect(state.oders) {
        viewModel.setInitialOders(state.oders)
    }

    LaunchedEffect(Unit) {
        delay(100)
        viewModel.loadHomeData(forceRefresh = false, "layhoahongthucte")
        viewModel.getReportShareLink("layhoahongthucte", startDate, endDate)
        viewModel.getActualCommissionByIdOder(status, "laychitiethoahong", startDate, endDate)
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
            type = type,
            onSearch = { search ->
                startDate = search.startDate
                endDate = search.endDate
                selectedType = search.selectedType
                viewModel.getReportShareLink("layhoahongthucte", startDate, endDate)
                viewModel.getActualCommissionByIdOder(
                    status, "laychitiethoahong", startDate, endDate
                )
                isFinterDialog = false
            })

    }

    Column {


        CustomBackTitle(title = title,
            tint = TealGreen,
            textColor = Color.Black,
            fontSize = 14.sp,
            painter = painterResource(id = R.drawable.quaylai),
            onBackPress = { navHostController.popBackStack() })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Dòng ngày + icon dropdown
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .noRippleClickableComposable { isFinterDialog = true }
                    .padding(10.dp)) {
                Text(
                    text = "$startDate - $endDate",
                    color = TealGreen,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Icon(
                    painter = painterResource(id = R.drawable.down),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Dòng tiền + icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 15.dp)
            ) {
                Text(
                    text = "đ", color = Color(0xFFFF5722), fontSize = 13.sp
                )
                Text(
                    text = state.sotienhuong.formatCurrency(),
                    color = Color(0xFFFF5722),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.info),
                    contentDescription = null,
                    modifier = Modifier
                        .size(14.dp)
                        .padding(start = 4.dp)
                )
            }
        }


        CustomSwipeRefresh(isRefreshing = isRefreshing,
            onRefresh = { viewModel.loadHomeData(forceRefresh = true, "layhoahongthucte") }) {
            LazyColumn(
                state = listState, modifier = Modifier
                    .fillMaxSize()
                    .background(FAFAFA)
            ) {

                if (state.isLoading) {
                    item {
                        CustomCircularProgressIndicatorDialog(show = isLoading,
                            onDismissRequest = { isLoading = false })
                    }
                }
                if (state.tabs.size > 1) {
                    stickyHeader {
                        TabBarRowLocal(
                            tabs = state.tabs,
                            selectedTab = state.selectedTab,
                            onTabSelected = {
                                viewModel.onCategorySelected(it, state.tabs)
                            })
                    }
                }

                if (state.pagedOders.isEmpty()) {
                    item {
                        EmptyStateScreen(
                            imageRes = R.drawable.emptycart,
                            size = 90.dp,
                            title = stringResource(R.string.empty_cart),
                            background = FFFAFAFA,
                            modifier = Modifier.padding(40.dp)
                        )
                    }

                } else {

                    val rows = state.pagedOders.chunked(1)

                    items(rows, key = { row -> row.firstOrNull()?.iddondathang ?: "row" }) { row ->
                        val order = row.firstOrNull()

                        if (order != null) {

                            val  coin = order.tongdiem?:0.0

                            val  commission = order.tonghoahong?:0.0

                            var  actualCommission = ""
                            var  isShowCoin = false;

                            if(state.pointAffiliate=="1"){

                                isShowCoin = true

                                actualCommission = "${stringResource(R.string.commission_label)}: ${coin}"
                            }else{
                                isShowCoin = false


                                actualCommission = "${stringResource(R.string.commission_label)}: ${commission.formatCurrency()}"
                            }


                            OrderItemViewIncome(
                                orderKey = "${stringResource(R.string.order_code_label)}: ${order.madonhangdathang}",
                                status = order.trangthai, // ví dụ: "Đã thanh toán"
                                totalCount = "Tổng: ${order.sanphams.size} ${stringResource(R.string.product)}",
                                actualCommission = actualCommission,
                                createDate = "${stringResource(R.string.createDate)}: ${order.ngaytao}",
                                showCoin = isShowCoin,
                                isExpanded = false,
                                onToggleClick = { /* mở rộng */ },
                                onArrowClick = { /* xử lý icon */ }
                            ) {
                                // Danh sách sản phẩm trong đơn hàng
                                LazyColumn {
                                    items(order.sanphams) { product ->
                                        val  coin = product.diem?:0.0
                                        val  commission = product.sotienhoahong?:0.0
                                        var  actualCommission = ""
                                        var  isShowCoin:Boolean = false
                                        if(state.pointAffiliate=="1"){
                                            isShowCoin = true
                                            actualCommission = "${stringResource(R.string.commission_label)}: ${coin}"
                                        }else{
                                            isShowCoin = false
                                            actualCommission = "${stringResource(R.string.commission_label)}: ${commission.formatCurrency()}"
                                        }
                                        ProductItemView(
                                            fullUrl ="${state.domain}${ product.filetxt}",
                                            name = product.tensanpham,
                                            count = product.soluong,
                                            commission = actualCommission,
                                            showCoin = isShowCoin
                                        )

                                    }
                                }
                            }
                        }
                    }

                }

            }
        }
    }
}
