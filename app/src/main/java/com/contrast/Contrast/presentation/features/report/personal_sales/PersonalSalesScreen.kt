package com.contrast.Contrast.presentation.features.report.personal_sales






import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.searchDialog.SearchConditionDialog


import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleFilter

import com.contrast.Contrast.presentation.features.report.viewmodel.ReportPersonalViewModel


import com.contrast.Contrast.presentation.navigator.routers.OpportunityRoutes
import com.contrast.Contrast.presentation.theme.LightGrayBackground


import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.SearchDialog

import com.itechpro.domain.model.navigationEvent.OpportunityNavEvent


import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonalSalesScreen(
    navHostController: NavHostController,

    title: String,
    viewModel: ReportPersonalViewModel = hiltViewModel(),


    ) {
    val state by viewModel.state.collectAsState()


    var isFinterDialog by remember { mutableStateOf(false) }
    val isRefreshing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var startDate by remember { mutableStateOf(DateUtils.today()) }
    var endDate by remember { mutableStateOf(DateUtils.today()) }

    var selectedType by remember { mutableStateOf("Ngày") }



    var typeDate by remember { mutableStateOf<DateFieldType>(DateFieldType.END) }



    LaunchedEffect(Unit) {
        delay(100)
        viewModel.getReportPersonalSales("dailyaf","bcdoanhsotieudungcanhan",startDate, endDate)

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
                viewModel.getReportPersonalSales("dailyaf","bcdoanhsotieudungcanhan",startDate, endDate)
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


            TableHeaderRow(
                sttTitle = stringResource(R.string.stt),
                contentTitle = stringResource(R.string.content),
                valueTitle = stringResource(R.string.monthlySales)
            )


        }

        CustomSwipeRefresh(isRefreshing = isRefreshing, onRefresh = {
            viewModel.getReportPersonalSales("dailyaf","bcdoanhsotieudungcanhan",startDate, endDate)

        }) {





           Column {  TableItemRow(
               "0",
               stringResource(id = R.string.order_waiting_confirmation),
               state.report?.soluongdonhangchoxacnhan.toString(),
               isBlue = true
           )

               CustomDividerColor()

               TableItemRow(
                   "1",
                   stringResource(id = R.string.order_confirmed),
                   state.report?.soluongdonhangdaxacnhan.toString(),
                   isBlue = true
               )

               CustomDividerColor()

               TableItemRow(
                   "2",
                   stringResource(id = R.string.total_before_discount),
                   (state.report?.tongtientruocchietkhau ?: 0.0).formatCurrency()
               )

               CustomDividerColor()

               TableItemRow(
                   "3",
                   stringResource(id = R.string.total_after_discount),
                   (state.report?.tongtiensauchietkhau ?: 0.0).formatCurrency()
               )

               CustomDividerColor() }

        }
    }
}


