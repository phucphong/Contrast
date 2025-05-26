package com.contrast.Contrast.presentation.features.order.list


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.alertDialog.ConfirmDeleteDialog
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.searchDialog.SearchConditionDialog

import com.contrast.Contrast.presentation.components.swipeDelete.SwipeRevealItem

import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle
import com.contrast.Contrast.presentation.features.order.viewmodel.OrderViewModel
import com.contrast.Contrast.presentation.features.order.list.item.OrderItemView
import com.itechpro.domain.model.navigationEvent.OrderNavEvent
import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes
import com.contrast.Contrast.presentation.navigator.routers.CartRoutes
import com.contrast.Contrast.presentation.navigator.routers.NotificationRoutes
import com.contrast.Contrast.presentation.navigator.routers.OrderRoutes
import com.contrast.Contrast.presentation.navigator.routers.ProductRoutes
import com.contrast.Contrast.presentation.navigator.routers.ServiceRequestRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.SearchDialog
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.model.order.Order

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrdersScreen(
    navHostController: NavHostController,
    type: String,
    title: String,
    viewModel: OrderViewModel = hiltViewModel(),


    ) {
    val state by viewModel.state.collectAsState()


    var showDeleteDialog by remember { mutableStateOf(false) }
    var isFinterDialog by remember { mutableStateOf(false) }
    val isRefreshing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var startDate by remember { mutableStateOf(DateUtils.today()) }
    var endDate by remember { mutableStateOf(DateUtils.today()) }

    var selectedType by remember { mutableStateOf("Ngày") }
    val listState = rememberLazyListState()
    var typeDate by remember { mutableStateOf<DateFieldType>(DateFieldType.END) }
    var openedItem by remember { mutableStateOf<Order?>(null) }
    var objToDelete by remember { mutableStateOf<Order?>(null) }
    LaunchedEffect(state.orders) {
        viewModel.setInitialOders(state.orders)
    }

    LaunchedEffect(Unit) {
        delay(100)
        callAPI(type, startDate, endDate, state.customerId, viewModel)

    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem?.index to totalItems
        }.distinctUntilChanged().debounce(300).collect { (lastIndex, total) ->
            if (lastIndex != null && total > 0 && lastIndex >= total - 2) {

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
            is OrderNavEvent.GoToOderDetail -> {
                navHostController.navigate(
                    OrderRoutes.OderDetail.withArgs(
                        id = event.id,
                        type = event.type,

                    )
                )
                viewModel.resetNavigation()
            }

            else -> Unit
        }
    }



    if (isFinterDialog) {
        var  obj = SearchDialog(startDate = startDate, endDate = endDate
            , selectedType = selectedType)

        SearchConditionDialog(onDismiss = { isFinterDialog = false },
            search = obj,
            type = typeDate,
            onSearch = { search ->
                startDate = search.startDate?:""
                endDate = search.endDate?:""
                selectedType = search.selectedType?:""
                callAPI(type, startDate, endDate, state.customerId, viewModel)
                isFinterDialog = false
            })

    }
    if (showDeleteDialog) {
        ConfirmDeleteDialog(show = showDeleteDialog, onDismiss = {
            showDeleteDialog = false
            objToDelete = null
            openedItem = null // 💡 đóng nút delete đang mở
        }, onConfirm = {
//                viewModel.removeOrder(objToDelete!!.id)

            showDeleteDialog = false
            objToDelete = null
            openedItem = null // 💡 đóng nút delete đang mở
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
            Row(

                modifier = Modifier
                    .wrapContentWidth()
                    .noRippleClickableComposable { isFinterDialog = true }
                    .padding(10.dp)) {

                Icon(
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(horizontal = 4.dp)
                )
                Text(
                    text = "$startDate - $endDate",
                    color = TealGreen,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp)
                )
                Icon(painter = painterResource(id = R.drawable.filter),
                    contentDescription = null,
                    tint = TealGreen,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(5.dp)
                        .noRippleClickableComposable {
                            isFinterDialog = true
                        })
            }

            Spacer(modifier = Modifier.height(6.dp))


        }


        CustomSwipeRefresh(isRefreshing = isRefreshing, onRefresh = {


            callAPI(type, startDate, endDate, state.customerId, viewModel)
        }) {
            LazyColumn(
                state = listState, modifier = Modifier
                    .fillMaxSize()
                    .background(FAFAFA)
            ) {


                if (state.isLoading) {
                    item {
                        CustomCircularProgressIndicatorDialog(
                            show = isLoading,
                            onDismissRequest = { isLoading = false })
                    }
                }else{
                    if (state.pagedOrders.isEmpty()) {
                        item {
                            EmptyStateScreen(
                                imageRes = R.drawable.emptycart,
                                size = 90.dp,
                                title = stringResource(R.string.empty_oder),
                                background = FFFAFAFA,
                                modifier = Modifier.padding(40.dp)
                            )
                        }

                    } else {

                        val rows = state.pagedOrders.chunked(1)

                        items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                            val order = row.firstOrNull()

                            if (order != null) {
                                val amountDebit = order.tongtienconno ?: 0.0
                                val totalAmount = order.tongtien ?: 0.0
                                val fullUrl = "${state.domain}${order.hinhanhtxt}"
                                var createDate = ""
                                if (order.ngaytao != null) {
                                    createDate = order.ngaytao ?: ""
                                }
                                if (order.ngaytaotxt != null) {
                                    createDate = order.ngaytaotxt ?: ""
                                }
                                var status = ""
                                if (amountDebit == 0.0) {
                                    status = stringResource(R.string.stillInDebt)
                                } else if (amountDebit == totalAmount) {
                                    status = stringResource(R.string.paymented)
                                }

                                if (type == "donhangchoxacnhan") {
                                    SwipeRevealItem(
                                        item = order,
                                        isOpen = openedItem == order,
                                        onSwipeStart = { swipedItem -> openedItem = swipedItem },
                                        onDeleteClick = { itemToDelete ->
                                            showDeleteDialog = true
                                            objToDelete =
                                                itemToDelete // 💡 lưu lại để xử lý sau khi confirm
                                        },
                                        paddingTop = 5.dp,
                                        paddingBottom = 5.dp
                                    ) { itemModifier ->
                                        OrderItemView(
                                            status = status,
                                            type = type,
                                            fullUrl = fullUrl,
                                            orderKey = "${stringResource(R.string.order)}: ${order.ma}",
                                            totalAmount = totalAmount.toString(),
                                            dateOrder = "${stringResource(R.string.createDate)}: $createDate",
                                            onItemClick={
                                                viewModel.onItemClick(order.id?:"", type)
                                            }
                                        )
                                    }
                                }else{
                                    OrderItemView(
                                        status = status,
                                        type = type,
                                        fullUrl = fullUrl,
                                        orderKey = "${stringResource(R.string.order)}: ${order.ma}",
                                        totalAmount = totalAmount.toString(),
                                        dateOrder = "${stringResource(R.string.createDate)}: $createDate",
                                        onItemClick={
                                            viewModel.onItemClick(order.id?:"", type)
                                        }
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

@RequiresApi(Build.VERSION_CODES.O)
fun callAPI(
    type: String,
    startDate: String,
    endDate: String,
    customerId: String,
    viewModel: OrderViewModel,
) {
    if (type == "donhang") {
        viewModel.getOderByTypeAccount(startDate, endDate, customerId)
    } else if (type == "donhangchoxacnhan") {
        viewModel.getOderByConfirm(startDate, endDate, "0")
    } else {
        viewModel.getOderByConfirm(startDate, endDate, "1")
    }
}