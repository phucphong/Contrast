package com.contrast.Contrast.presentation.features.affiliate.category


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabLocal
import com.contrast.Contrast.presentation.components.tab.TabBarRow
import com.contrast.Contrast.presentation.components.tab.TabBarRowCircle
import com.contrast.Contrast.presentation.components.tab.TabBarRowPillStyle
import com.contrast.Contrast.presentation.features.product.ui.ProductGridAffiliate
import com.contrast.Contrast.presentation.theme.TealGreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CategoryAffiliatePage(
    navHostController: NavHostController,
    categoryId: String,
    viewModel: CategoryAffiliateModel = hiltViewModel()
) {
    val products by viewModel.products.collectAsState()

    val category1 by viewModel.category1.collectAsState()
    val category2 by viewModel.category2.collectAsState()
    val category3 by viewModel.category3.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val displayProduct by viewModel.displayProduct.collectAsState()
    val displayService by viewModel.displayService.collectAsState()
    val displayPriority by viewModel.displayPriority.collectAsState()

    val type by viewModel.type.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val selectedTab by viewModel.selectedTab.collectAsState()
    val selectedTab1 by viewModel.selectedTab1.collectAsState()
    val selectedTab2 by viewModel.selectedTab2.collectAsState()
    val selectedTab3 by viewModel.selectedTab3.collectAsState()
    val isRefreshing by remember { mutableStateOf(false) }
    var isBackStack by remember { mutableStateOf(false) }


    val isLoading by viewModel.isLoading.collectAsState()
    var searchText by remember { mutableStateOf("") }
    val tabs by viewModel.tabs.collectAsState()
    val isInit = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {


        if (!isInit.value) {
            viewModel.initCategory(displayProduct, displayService, displayPriority,categoryId)
            isInit.value = true
        }
    }

    LaunchedEffect(categoryId) {
        Log.e("categoryId",categoryId)

        if(categoryId!="0"){
            isBackStack = true
        }

    }



    Column(modifier = Modifier.fillMaxSize()) {
        TopSearchNotificationCart(
            isTexField = true,
            isBackStack = isBackStack,
            text = searchText,
            onTextChanged = { searchText = it },
            onSearchClick = { /* mở trang tìm kiếm */ },
            onNotificationClick = { /* xử lý noti */ },
            onCartClick = { },
            onBackStack = {   navHostController.popBackStack() }
        )

        if (isLoading) {
            // Hiển thị loading, ví dụ:
            Box(
                modifier = Modifier.fillMaxSize().padding(top = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CustomCircularProgressIndicator()
            }
        }

        if (tabs.size > 1) {
            SegmentTabLocal(
                tabs = tabs,
                selectedTab = selectedTabIndex,
                type = "name",
                onTabSelected = {
                    selectedTabIndex = it
//                    viewModel.getCategory1("tatcanhomsp", "tatcanhomsp", type, "0", 1)

                    viewModel.onCategorySelected(it, category1, categoryId)
                }
            )
        }
        TabBarRow(
            tabs = category1,
            color = TealGreen,
            textCorSelect = TealGreen,
            selectedTab = selectedTab1,
            onTabSelected = {
                viewModel.onCategory1Selected(it, category1, type)
            },
            type = "name"
        )

        // ✅ Scroll tất cả nội dung chung trong LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column {


                    TabBarRowCircle(
                        tabs = category2,
                        color = TealGreen,
                        textCorSelect = TealGreen,
                        selectedTab = selectedTab2,
                        type = "name",
                        domain = domain,
                        onTabSelected = {
                            viewModel.onCategory2Selected(it, category2, type)
                        }
                    )

                    TabBarRowPillStyle(
                        tabs = category3,
                        selectedTab = selectedTab3,
                        type = "name",
                        onTabSelected = {
                            viewModel.onCategory3Selected(it, category3, type)
                        }
                    )

                    CustomDividerColor()

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
}




