package com.contrast.Contrast.presentation.features.affiliate.home


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.slider.ImageSliderFromUrl
import com.contrast.Contrast.presentation.components.tab.TabBarPagedGridScrollable
import com.contrast.Contrast.presentation.components.tab.TabBarRowLocal
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.itechpro.domain.model.navigationEvent.ProductNavEvent

import com.contrast.Contrast.presentation.features.product.ui.ProductGridAffiliate
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFD9D9D9
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent


@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun HomeAffiliatePage(
    navHostController: NavHostController,
    viewModel: HomeAffiliateViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val slides by viewModel.slides.collectAsState()
    val categorys by viewModel.categorys.collectAsState()
    val flashSales by viewModel.flashSales.collectAsState()
    val tabs by viewModel.tabs.collectAsState()
    val products by viewModel.products.collectAsState()
    val totalCartItems by cartViewModel.totalCartItems.collectAsState()
    val totalNotificationItems by notificationViewModel.totalNotificationItems.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val displayProduct by viewModel.displayProduct.collectAsState()
    val displayService by viewModel.displayService.collectAsState()
    val displayPriority by viewModel.displayPriority.collectAsState()

    val selectedTab by viewModel.selectedTab.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }
    val isInit = remember { mutableStateOf(false) }
    val navEvent by viewModel.navigationEvent.collectAsState()

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


    LaunchedEffect(Unit) {
        if (!isInit.value) {
            viewModel.initCategory(displayProduct, displayService, displayPriority)
            isInit.value = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {


        TopSearchNotificationCart(
            isTexField = true,
            text = searchText,
            totalNotificationItems = totalNotificationItems,
            totalCartItems = totalCartItems,
            onTextChanged = { searchText = it },
            onSearchClick = { /* mở trang tìm kiếm */ },
            onNotificationClick = { viewModel.onItemNotificationSelected()},
            onCartClick = { /* xử lý cart */ }
        )
//        if (isLoading) {
//            // Hiển thị loading, ví dụ:
//            Box(
//                modifier = Modifier.fillMaxSize().padding(top = 20.dp),
//                contentAlignment = Alignment.TopCenter
//            ) {
//                CustomCircularProgressIndicator()
//            }
//        }

        LazyColumn(
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
                CustomDividerColor(color = FFD9D9D9, padding=5.dp)
            }
            // ✅ stickyHeader phải nằm ngoài item {}
            if (tabs.size > 1) {
                stickyHeader {
                    TabBarRowLocal (
                        tabs = tabs,
                        selectedTab = selectedTab,
                        onTabSelected = {
                            viewModel.onCategorySelected(it, tabs)
                        }

                    )
                }
            }



            item {
                if (products.isNotEmpty()) {
                    domain?.let {

                            ProductGridAffiliate(
                                domain = it,
                                products = products,

                                onItemClick={
                                    viewModel.onItemProductSelected( it)

                                },
                                onClickCart={viewModel.onItemCart( it)},
                                onClickAddServiceRequest={viewModel.onAddServiceRequestSelected( it)},
                                modifier = Modifier.fillMaxWidth().height(500.dp)
                            )

                    }
                }else{
                    Box(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}



