package com.contrast.Contrast.presentation.features.customer.ui.detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.bottomAction.BottomAction
import com.contrast.Contrast.presentation.components.bottomAction.BottomActionList
import com.contrast.Contrast.presentation.components.header.HeaderImageTitle
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.SegmentTab
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.chat.ChatInputBox
import com.contrast.Contrast.presentation.features.customer.viewmodel.CustomerViewModel
import com.contrast.Contrast.presentation.features.detail.InfoItemDetail
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.itechpro.domain.model.InfoDetail
import com.itechpro.domain.model.navigationEvent.CustomerNavigationEvent

@Composable
fun CustomerDetailScreen(
    ido: String,
    onBackClick: () -> Unit = {},
    viewModel: CustomerViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val tabTitles = listOf(
        stringResource(R.string.info),
        stringResource(R.string.other),
        stringResource(R.string.timeline),
        stringResource(R.string.exchange)
    )
    var selectedTabIndex by remember { mutableStateOf(0) }
//    val customerInfoList = getCustomerInfoList()

    val customerInfoList by viewModel.customerInfo.collectAsState()
    val customerInfoOtherList  by viewModel.customerOther.collectAsState()
    val timelineList   by viewModel.customerOther.collectAsState()
    val exchangeList   by viewModel.customerOther.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is CustomerNavigationEvent.GoToEdit -> {
                    navController.navigate("eit_customer_screen")
                }
                is CustomerNavigationEvent.GoToContact -> {
                    navController.navigate("contact_customer_screen")
                }
                // v.v...
                CustomerNavigationEvent.GoToOpportunity ->{
                    navController.navigate("opportunity_tab_screen")
                }
                CustomerNavigationEvent.GoToTask -> {
                    navController.navigate("task_screen")
                }
                CustomerNavigationEvent.GoToMore ->{
                    navController.navigate("more_customer_screen")
                }
            }
        }
    }
    

    LaunchedEffect(ido) {
        if (ido != "0") {
            viewModel.getCustomerDetail(ido)
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { selectedTabIndex }
            .collect { index ->
                when (index) {
                    2 -> viewModel.getTimelineData(ido)  // API load timeline
                    3 -> viewModel.getExchangeData(ido)  // API load exchange
                }
            }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomTopAppBarBackTitleSave(
            title = stringResource(R.string.custom_detail),
            backgroundColor = FAFAFA,
            fontSize = 14.sp,
            onBackClick = {onBackClick },
        )

        val isRefreshing by remember { mutableStateOf(false) }
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

        CustomSwipeRefresh(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.getCustomerDetail(ido) }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    HeaderImageTitle(
                        name = "customerName",
                        iconRes = R.drawable.vkhachhangcanhan
                    )
                }

                item {
                    SegmentTab(
                        tabs = tabTitles,
                        selectedTab = selectedTabIndex,
                        onTabSelected = { selectedTabIndex = it }
                    )
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }








                item {
                    val infoList = when (selectedTabIndex) {
                        0 -> customerInfoList
                        1 -> customerInfoOtherList
                        2 -> timelineList
                        3 -> exchangeList
                        else -> emptyList()
                    }


                    if (infoList != null) {
                        infoList.forEach { info ->

                            when (selectedTabIndex) {
                                0 -> InfoItemDetail(info = info)
                                1 ->InfoItemDetail(info = info)
                                2 -> TimelineCustomer(info = info)
                                3 -> ExchangeCustomer(info = info)
                                else ->{

                                }
                            }

                        }
                    }else{
                        EmptyStateScreen(
                            imageRes = R.drawable.nodata,
                            size = 60.dp,
                            title = stringResource(R.string.no_data),
                        )
                    }
                }
            }
        }



        BottomActionList(
            actions = viewModel.bottomActions,
            onItemClick = { item ->
                viewModel.onBottomActionClick(item.id)
            }
        )
    }

    if(selectedTabIndex==3){
        ChatInputBox(
            text = "",
            onTextChange = {},
            onSendClick = {},
            onAttachClick = {}
        )
    }
}

