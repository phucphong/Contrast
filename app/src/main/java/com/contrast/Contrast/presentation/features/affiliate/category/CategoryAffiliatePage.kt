package com.contrast.Contrast.presentation.features.affiliate.category

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabLocal
import com.contrast.Contrast.presentation.components.tab.TabBarRow
import com.contrast.Contrast.presentation.components.tab.TabBarRowCircle
import com.contrast.Contrast.presentation.components.tab.TabBarRowPillStyle
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.ui.ProductRow
import com.contrast.Contrast.presentation.navigator.router.routes.CartRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.NotificationRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.ProductRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.ServiceRequestRoutes
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.navigationEvent.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CategoryAffiliatePage(
    navHostController: NavHostController,
    categoryId: String,
    viewModel: CategoryAffiliateModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val promoUiDataMap = viewModel.promoUiDataMap

    val cartState by cartViewModel.state.collectAsState()
    val notificationState by notificationViewModel.state.collectAsState()


    var selectedTabIndex by remember { mutableStateOf(0) }
    var searchText by remember { mutableStateOf("") }
    val isInit = remember { mutableStateOf(false) }
    var isBackStack by remember { mutableStateOf(false) }



    LaunchedEffect(state.products) {
        viewModel.setInitialProducts(state.products)
    }

    LaunchedEffect(Unit) {
        if (!isInit.value) {
            viewModel.initCategory(state.displayProduct, state.displayService, state.displayPriority, categoryId)
            isInit.value = true
            cartViewModel.getCarts(false)
        }
    }

    LaunchedEffect(categoryId) {
        if (categoryId != "0") {
            isBackStack = true
        }
    }

    LaunchedEffect(state.navEvent) {
        when (val event = state.navEvent) {
            is NotificationNavEvent.GoToNotifications -> {
                navHostController.navigate(
                    NotificationRoutes.Notifications.withArgs(event.startDate, event.endDate)
                )
                viewModel.resetNavigation()
            }
            is CartNavEvent.GoToCats -> {
                navHostController.navigate(CartRoutes.Carts.route)
                viewModel.resetNavigation()
            }
            is ProductNavEvent.GoToProductsCategory -> {
                navHostController.navigate(ProductRoutes.ProductByCategory.withArgs(event.categoryId))
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

    Column(modifier = Modifier.fillMaxSize()) {
        TopSearchNotificationCart(
            isTexField = true,
            isBackStack = isBackStack,
            text = searchText,
            onTextChanged = { searchText = it

                viewModel.searchLocal (searchText, state.products)
                            },
            totalNotificationItems = notificationState.totalNotificationItems,
            totalCartItems = cartState.totalCartItems,
            onNotificationClick = { viewModel.onItemNotificationSelected() },
            onCartClick = { viewModel.onItemCarts() },
            onBackStack = { navHostController.popBackStack() }
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(top = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CustomCircularProgressIndicator()
            }
        }

        if (state.tabs.size > 1) {
            SegmentTabLocal(
                tabs = state.tabs,
                selectedTab = selectedTabIndex,
                onTabSelected = {
                    searchText = ""
                    selectedTabIndex = it
                    viewModel.onCategorySelected(it, state.tabs, categoryId)
                }
            )
        }

        TabBarRow(
            tabs = state.category1,
            color = TealGreen,
            textCorSelect = TealGreen,
            selectedTab = state.selectedTab1,
            onTabSelected = { viewModel.onCategory1Selected(it, state.category1, state.type) },
            type = "name"
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                TabBarRowCircle(
                    tabs = state.category2,
                    color = TealGreen,
                    textCorSelect = TealGreen,
                    selectedTab = state.selectedTab2,
                    type = "name",
                    domain = state.domain,
                    onTabSelected = { viewModel.onCategory2Selected(it, state.category2, state.type) }
                )
            }
            item {
                TabBarRowPillStyle(
                    tabs = state.category3,
                    selectedTab = state.selectedTab3,
                    type = "name",
                    onTabSelected = { viewModel.onCategory3Selected(it, state.category3, state.type) }
                )
                CustomDividerColor()
            }

            val rows = state.pagedProducts.chunked(2)
            items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                ProductRow(
                    domain = state.domain,
                    token = state.token,
                    pointAffiliate = state.pointAffiliate,
                    rowProducts = row,
                    promoUiDataMap = promoUiDataMap,
                    onItemClick = { viewModel.onItemProductSelected(it) },
                    onClickCart = { cartViewModel.onItemAddCart(it) },
                    onClickAddServiceRequest = { viewModel.onAddServiceRequestSelected(it) },
                    onClickShare = { }
                )
            }
        }
    }
}
