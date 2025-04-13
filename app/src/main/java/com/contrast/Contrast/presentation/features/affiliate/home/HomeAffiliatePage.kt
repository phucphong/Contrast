package com.contrast.Contrast.presentation.features.affiliate.home


import android.os.Build
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.slider.ImageSliderFromUrl
import com.contrast.Contrast.presentation.components.tab.TabBarPagedGridScrollable
import com.contrast.Contrast.presentation.components.tab.TabBarGridStyle
import com.contrast.Contrast.presentation.components.tab.TabBarRow
import com.contrast.Contrast.presentation.features.product.ui.ProductGridAffiliate


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

    val type by viewModel.type.collectAsState()
    val modeApi by viewModel.modeApi.collectAsState()
    val objApi by viewModel.objApi.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedCategory by remember { mutableStateOf(0) }
    var selectedTab2 by remember { mutableStateOf(0) }
    var selectedTab3 by remember { mutableStateOf(0) }
    val selectedTab by viewModel.selectedTab.collectAsState()
    val isRefreshing by remember { mutableStateOf(false) }
    var idCategory by remember { mutableStateOf("0") }

    val isLoading by viewModel.isLoading.collectAsState()


    val isInit = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!isInit.value) {
            viewModel.initCategory(displayProduct, displayService, displayPriority)
            isInit.value = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopSearchNotificationCart()


        // ✅ Scroll tất cả nội dung chung trong LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column {


                    if (slides.isNotEmpty()) {
                        ImageSliderFromUrl(domain, autoScroll = true, slides, modifier = Modifier.height(220.dp))
                    }
                    if (slides.isNotEmpty()) {
                        TabBarPagedGridScrollable(
                            tabs = categorys,
                            selectedTab = selectedCategory,
                            domain = domain,
                            type = "name", // hoặc "name"
                            onTabSelected = {
//                            viewModel.onCategory2Selected(it, category2, type)
                            }
                        )
                    }





//                    if (tabs.size > 1) {
//                        TabBarRow(
//                            tabs = tabs,
//                            selectedTab = selectedTabIndex,
//                            type = "name",
//                            onTabSelected = {
//                                selectedTabIndex = it
//
//                            }
//                        )
//                    }



                    CustomDividerColor()

//                    domain?.let {
//                        ProductGridAffiliate(
//                            domain = it,
//                            products = flashSales,
//                            modifier = Modifier.fillMaxWidth()
//                        )
//                    }
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
}




