package com.contrast.Contrast.presentation.features.report.report_passive_commission






import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.alertDialog.ConfirmDeleteDialog
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.searchDialog.SearchConditionDialog
import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabLocal
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleFilter
import com.contrast.Contrast.presentation.features.report.viewmodel.ReportPersonalViewModel
import com.contrast.Contrast.presentation.navigator.routers.OpportunityRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.contrast.Contrast.presentation.theme.LightGrayBackground
import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.navigationEvent.OpportunityNavEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportPassiveCommissionScreen(
    navHostController: NavHostController,
    title: String,
    viewModel: ReportPersonalViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()


    var isFinterDialog by remember { mutableStateOf(false) }
    val isRefreshing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var monthYear by remember { mutableStateOf(DateUtils.getCurrentMonthYear()) }
    val listState = rememberLazyListState()
    var startDate by remember { mutableStateOf(DateUtils.today()) }
    var endDate by remember { mutableStateOf(DateUtils.today()) }
    var ids by remember { mutableStateOf("") }
    var idsAgencyLevel by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Ngày") }
    var typeDate by remember { mutableStateOf<DateFieldType>(DateFieldType.END) }



    LaunchedEffect(Unit) {
        delay(100)
        viewModel.getReportPassiveCommission(monthYear)

    }
    LaunchedEffect(state.reports) {
        viewModel.setInitialReports(state.reports)
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
        SearchConditionDialog(onDismiss = { isFinterDialog = false },
            startDate = startDate,
            endDate = endDate,
            selectedType = selectedType,
            type = typeDate,
            onSearch = { search ->
                startDate = search.startDate
                endDate = search.endDate
                selectedType = search.selectedType
                viewModel.getReportPassiveCommission(monthYear)
                isFinterDialog = false
            })

    }

    Column {

        CustomTopAppBarBackTitleFilter(
            title = title,
            painter = painterResource(id = R.drawable.quaylai),
            fontSize = 14.sp,
            onBackClick = { navHostController.popBackStack() },
            onFilterClick = {
                isFinterDialog = true
            },
        )

        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightGrayBackground)

        ) {

            TableHeaderCommissionRow(
                sttTitle = stringResource(R.string.name),
                contentTitle = stringResource(R.string.total_revenue),
                valueTitle = stringResource(R.string.received_amount),
                discount = stringResource(R.string.discount),
            )


        }


        CustomSwipeRefresh(isRefreshing = isRefreshing, onRefresh = {
            viewModel.getReportPassiveCommission(monthYear)

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
                } else {
                    if (state.pagedReports.isEmpty()) {
                        item {
                            EmptyStateScreen(
                                imageRes = R.drawable.emptycart,
                                size = 90.dp,
                                title = stringResource(R.string.empty_report),
                                background = FFFAFAFA,
                                modifier = Modifier.padding(40.dp)
                            )
                        }

                    } else {

                        val rows = state.pagedReports.chunked(1)

                        itemsIndexed(
                            rows,
                            key = { index, row ->
                                row.firstOrNull()?.id ?: "row_$index"
                            }) { index, row ->
                            val item = row.firstOrNull()

                            if (item != null) {

                                val count = index + 1

                                val key = item.ma ?: ""
                                val name = item.hoten ?: ""
                                val level = item.capdodaily ?: ""
                                var fullname = "$count.$key-$name-\n$level"

                                TableItemComminssionRow(
                                    fullname,
                                    (item.tongdoanhso?:0.0).formatDouble(),
                                    (item.sotienduochuong?:0.0).formatCurrency(),
                                    (item.phantramhuong?:0.0).formatDouble()
                                )

                            }
                        }

                    }
                }

            }


        }
    }
}


