package com.contrast.Contrast.presentation.features.affiliate.home


import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.slider.ImageSliderFromUrl
import com.contrast.Contrast.presentation.components.tab.TabBarPagedGridScrollable
import com.contrast.Contrast.presentation.components.tab.TabBarRowLocal
import com.contrast.Contrast.presentation.navigator.event.HomeNavEvent

import com.contrast.Contrast.presentation.features.product.ui.ProductGridAffiliate
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFD9D9D9


@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun HomeAffiliatePage(
    navHostController: NavHostController,
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
                navHostController.navigate(NavRoutes.ProductByCategory.createRoute(event.categoryId))
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
                            onItemClick={

                                Log.e("onItemClick","onItemClick")
                            },
                            modifier = Modifier.fillMaxWidth()
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




