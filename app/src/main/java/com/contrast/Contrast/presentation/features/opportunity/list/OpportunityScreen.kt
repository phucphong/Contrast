package com.contrast.Contrast.presentation.features.opportunity.list




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.alertDialog.ConfirmDeleteDialog
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.header.HeaderImageTitle
import com.contrast.Contrast.presentation.components.searchBar.TopBackSearchFilter
import com.contrast.Contrast.presentation.components.searchDialog.SearchConditionDialog

import com.contrast.Contrast.presentation.components.swipeDelete.SwipeRevealItem

import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.TabBarRow
import com.contrast.Contrast.presentation.features.opportunity.OpportunityViewModel
import com.contrast.Contrast.presentation.features.opportunity.list.item.OpportunityItem

import com.contrast.Contrast.presentation.navigator.routers.OpportunityRoutes

import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.utils.Util
import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.SearchDialog

import com.itechpro.domain.model.navigationEvent.OpportunityNavEvent

import com.itechpro.domain.model.opportunity.Opportunity


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OpportunityScreen(
    navHostController: NavHostController,
    type: String,
    title: String,
    viewModel: OpportunityViewModel = hiltViewModel(),


    ) {
    val state by viewModel.state.collectAsState()


    var showDeleteDialog by remember { mutableStateOf(false) }
    var isFinterDialog by remember { mutableStateOf(false) }
    val isRefreshing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var startDate by remember { mutableStateOf(DateUtils.today()) }
    var endDate by remember { mutableStateOf(DateUtils.today()) }

    var selectedType by remember { mutableStateOf("Ngày") }
    var searchText by remember { mutableStateOf("") }
    var statusId by remember { mutableStateOf("0") }

    val listState = rememberLazyListState()
    var typeDate by remember { mutableStateOf<DateFieldType>(DateFieldType.END) }
    var openedItem by remember { mutableStateOf<Opportunity?>(null) }
    var objToDelete by remember { mutableStateOf<Opportunity?>(null) }
    LaunchedEffect(state.opportunitys) {
        viewModel.setInitialOpportunitys(state.pagedOpportunitys)
    }

    LaunchedEffect(Unit) {
        delay(100)

        callAPI(type, startDate, endDate,  searchText,statusId,viewModel)

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
            is OpportunityNavEvent.GoToOpportunityDetail -> {
                navHostController.navigate(
                    OpportunityRoutes.OpportunityDetail.withArgs(
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
                callAPI(type, startDate, endDate,  searchText,statusId,viewModel)
                isFinterDialog = false
            })

    }
    if (showDeleteDialog) {
        ConfirmDeleteDialog(show = showDeleteDialog, onDismiss = {
            showDeleteDialog = false
            objToDelete = null
            openedItem = null // 💡 đóng nút delete đang mở
        }, onConfirm = {
            
            var  content = "";
            val  name = objToDelete?.ten?:""
            val  customerName = "- ${objToDelete?.ten?:""}"
            content =name+customerName

            viewModel.deleteOpportunity(type,"cohoikinhdoanh",objToDelete?.id?:"", "cohoikinhdoanh", content, startDate, endDate, searchText, state.categoryCode, state.categorys)

            showDeleteDialog = false
            objToDelete = null
            openedItem = null // 💡 đóng nút delete đang mở
        })


    }

    Column {

        TopBackSearchFilter(
            painter = painterResource(R.drawable.quaylai),
            text = searchText,
            isFilter =true,
            onTextChanged = { searchText = it },
            onSearchClick = {
                callAPI(type, startDate, endDate,  searchText,statusId,viewModel)
            },
            onBackStack = { navHostController.popBackStack() },
            onFilterClick = { isFinterDialog = true }
        )

        HeaderImageTitle(
            name = title,
            iconRes = R.drawable.vcohoikinhdoanh

        )

        CustomSwipeRefresh(isRefreshing = isRefreshing, onRefresh = {

            callAPI(type, startDate, endDate,  searchText,statusId,viewModel)
        }) {
            LazyColumn(
                state = listState, modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                item {
                TabBarRow(
                    tabs = state.categorys,
                    color = TealGreen,
                    textCorSelect = TealGreen,
                    selectedTab = state.selectedTab,
                    isQuantity=true,
                    onTabSelected = { viewModel.onCategorySelected(it, state.categorys, type, startDate,endDate,searchText) },
                    type = ""
                )
                }

                if (state.isLoading) {
                    item {
                        CustomCircularProgressIndicatorDialog(
                            show = isLoading,
                            onDismissRequest = { isLoading = false })
                    }
                }else{
                    if (state.pagedOpportunitys.isEmpty()) {
                        item {
                            EmptyStateScreen(
                                imageRes = R.drawable.nodata,
                                size = 90.dp,
                                title = stringResource(R.string.youNotHaveOpportunities),
                                background = FFFAFAFA,
                                modifier = Modifier.padding(40.dp)
                            )
                        }

                    } else {
                        val rows = state.pagedOpportunitys.chunked(1)
                        items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                            val item = row.firstOrNull()

                            if (item != null) {


                                SwipeRevealItem(
                                    item = item,
                                    isOpen = openedItem == item,
                                    onSwipeStart = { swipedItem -> openedItem = swipedItem },
                                    onDeleteClick = { itemToDelete ->
                                        showDeleteDialog = true
                                        objToDelete =
                                            itemToDelete // 💡 lưu lại để xử lý sau khi confirm
                                    },
                                    paddingTop = 5.dp,
                                    paddingBottom = 5.dp
                                ) { itemModifier ->
                                    val   startDate = Util.ddMMYYY(item.ngaybatdau ?: "")
                                    val endDate = " - " + Util.ddMMYYY(item.ngayketthuc ?: "")

                                    OpportunityItem(
                                        name = item.ten?:"",
                                        key =item.ma?:"",
                                        total = ( item.tongiatri?:0.0).formatCurrency(),
                                        customer =item.tenkhachhang?:"",
                                        blankWork = "${( item.tylethanhcong?:0.0).formatCurrency()}%",
                                        complete = "Hoàn thành",
                                        date = "$startDate $endDate",
                                        personInCharge = item.nhanvienphutrach?:""
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
    searchText: String,
    statusId: String,
    viewModel: OpportunityViewModel,
) {

    viewModel.getOpportunityProcess(type,startDate,endDate, searchText,statusId)
    
}