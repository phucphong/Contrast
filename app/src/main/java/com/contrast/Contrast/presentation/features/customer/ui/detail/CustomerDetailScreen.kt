package com.contrast.Contrast.presentation.features.customer.ui.detail

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
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.bottomAction.BottomAction
import com.contrast.Contrast.presentation.components.header.HeaderImageTitle
import com.contrast.Contrast.presentation.components.tab.SegmentTab
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.chat.ChatInputBox
import com.contrast.Contrast.presentation.features.customer.viewmodel.CustomerViewModel
import com.contrast.Contrast.presentation.features.detail.InfoItemDetail
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.itechpro.domain.model.InfoDetail

@Composable
fun CustomerDetailScreen(
    ido: String,
    onEditClick: () -> Unit = {},
    onContactClick: () -> Unit = {},
    onBusinessClick: () -> Unit = {},
    onTaskClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}, viewModel: CustomerViewModel = hiltViewModel()
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
    val timelineList = getCustomerInfoList()

    LaunchedEffect(ido) {
        if (ido != "0") {
            viewModel.customerDetail(ido)
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { selectedTabIndex }
            .collect { index ->
                when (index) {
                    2 -> viewModel.loadTimelineData(ido)  // API load timeline
                    3 -> viewModel.loadExchangeData(ido)  // API load exchange
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
            onBackClick = { },
        )

        val isRefreshing by remember { mutableStateOf(false) }
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .weight(1f),
            state = swipeRefreshState,
            onRefresh = {}
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
                        3 -> customerInfoOtherList
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
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomAction(icon = R.drawable.ic_edit, label = stringResource(R.string.edit), onClick = onEditClick)
            BottomAction(icon = R.drawable.ic_contact, label = stringResource(R.string.contact), onClick = onContactClick)
            BottomAction(icon = R.drawable.ic_opportunity, label = stringResource(R.string.business_opportunity), onClick = onBusinessClick)
            BottomAction(icon = R.drawable.ic_task, label = stringResource(R.string.task), onClick = onTaskClick)
            BottomAction(icon = R.drawable.ic_more, label = stringResource(R.string.more), onClick = onMoreClick)
        }
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

@Composable
fun getCustomerInfoList(): List<InfoDetail> {
    return listOf(
        InfoDetail(label = stringResource(R.string.customer_type), value = "Khách hàng cá nhân"),
        InfoDetail(label = stringResource(R.string.customer_code), value = "00204"),
        InfoDetail(label = stringResource(R.string.customer_name), value = "Anh Phúc  Phong"),
        InfoDetail(label = stringResource(R.string.phone_number), value = "0964931225", highlight = true),
        InfoDetail(label = stringResource(R.string.address), value = ""),
        InfoDetail(label = stringResource(R.string.date_of_birtd), value = ""),
        InfoDetail(label = stringResource(R.string.email), value = ""),
        InfoDetail(label = stringResource(R.string.website), value = ""),
        InfoDetail(label = stringResource(R.string.map), value = ""),
        InfoDetail(label = stringResource(R.string.level), value = ""),
        InfoDetail(label = stringResource(R.string.policy_group), value = ""),
        InfoDetail(label = stringResource(R.string.industry_group), value = ""),
        InfoDetail(label = stringResource(R.string.status), value = ""),
        InfoDetail(label = stringResource(R.string.country), value = ""),
        InfoDetail(label = stringResource(R.string.province), value = ""),
        InfoDetail(label = stringResource(R.string.district), value = ""),
        InfoDetail(label = stringResource(R.string.ward), value = "")
    )
}
@Composable
fun getCustomerInfoOtherList(): List<InfoDetail> {
    return listOf(
        InfoDetail(stringResource(R.string.customer_category), "Khách hàng cá nhân"),
        InfoDetail(stringResource(R.string.auto_type), "00204"),
        InfoDetail(stringResource(R.string.information_source), "Anh Phúc  Phong"),
        InfoDetail(stringResource(R.string.bank_account), "0964931225", highlight = true),
        InfoDetail(stringResource(R.string.bank_name), ""),
        InfoDetail(stringResource(R.string.introducer), ""),
        InfoDetail(stringResource(R.string.care_date), ""),
        InfoDetail(stringResource(R.string.care_staff), ""),
        InfoDetail(stringResource(R.string.facebook), ""),
        InfoDetail(stringResource(R.string.zalo), ""),
        InfoDetail(stringResource(R.string.note), "")
    )
}
