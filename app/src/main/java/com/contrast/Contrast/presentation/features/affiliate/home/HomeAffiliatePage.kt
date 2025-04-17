package com.contrast.Contrast.presentation.features.affiliate.home


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabLocal
import com.contrast.Contrast.presentation.components.slider.ImageSliderFromUrl
import com.contrast.Contrast.presentation.components.tab.TabBarPagedGridScrollable
import com.contrast.Contrast.presentation.components.tab.TabBarRowLocal
import com.contrast.Contrast.presentation.features.navigator.HomeNavEvent

import com.contrast.Contrast.presentation.features.product.ui.ProductGridAffiliate
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.theme.FFAFAFAF
import com.contrast.Contrast.presentation.theme.FFD7D7D7
import com.contrast.Contrast.presentation.theme.FFD9D9D9
import com.contrast.Contrast.presentation.theme.TealGreen


@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun HomeAffiliatePage(
    navController: NavController,
    viewModel: HomeAffiliateModel = hiltViewModel()
) {
    val slides by viewModel.slides.collectAsState()
    val categorys by viewModel.categorys.collectAsState()
    val flashSales by viewModel.flashSales.collectAsState()
    val tabs by viewModel.tabs.collectAsState()
    val products by viewModel.products.collectAsState()

    val domain by viewModel.domain.collectAsState()
    val displayProduct by viewModel.displayProduct.collectAsState()
    val displayService by viewModel.displayService.collectAsState()
    val displayPriority by viewModel.displayPriority.collectAsState()

    val selectedTab by viewModel.selectedTab.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(0) }
    var idCategory by remember { mutableStateOf("0") }
    val isInit = remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val navEvent by viewModel.navigationEvent.collectAsState()

    LaunchedEffect(navEvent) {
        when (val event = navEvent) {
            is HomeNavEvent.GoToProduct -> {
                if (navController.graph.startDestinationRoute != null) {
                    navController.navigate("product/${event.categoryId}")
                    viewModel.resetNavigation()
                }
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
            onTextChanged = { searchText = it },
            onSearchClick = { /* mở trang tìm kiếm */ },
            onNotificationClick = { /* xử lý noti */ },
            onCartClick = { /* xử lý cart */ }
        )

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
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}




