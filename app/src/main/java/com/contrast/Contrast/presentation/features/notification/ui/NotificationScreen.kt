package com.contrast.Contrast.presentation.features.notification.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FCFCFC

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.extensions.DateUtils

import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicator
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationScreen(navHostController: NavHostController,
                       startDateInit:String,
                       endDatenit:String,

                       viewModel: NotificationViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()



    val isRefreshing by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf(startDateInit) }
    var endDate by remember { mutableStateOf(endDatenit) }


    val navEvent by viewModel.navigationEvent.collectAsState()
//    LaunchedEffect(navEvent) {
//        when (val event = navEvent) {
//            is NotificationNavEvent.GoToOrderDetail -> {
//                navHostController.currentBackStackEntry?.savedStateHandle?.apply {
//                    set("orderId", event.id) // ✅ dùng đúng tên field trong event
//                }
//                navHostController.navigate(NavRoutes.OrderDetail)
//                viewModel.resetNavigation()
//            }
//
//            else -> Unit
//        }
//    }


    LaunchedEffect(Unit) {
        startDate = DateUtils.today()
        endDate = DateUtils.today()
    }

    LaunchedEffect(state.notifications) {
        viewModel.getNotifications(startDate,endDate)
    }


    Column(modifier = Modifier.fillMaxSize()) {



        CustomBackTitle  (
            title = stringResource(R.string.notification_title),
            onBackPress = { navHostController.popBackStack()}
        )
        if (state.isLoading) {
            // Hiển thị loading, ví dụ:
            Box(
                modifier = Modifier.fillMaxSize().padding(top = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CustomCircularProgressIndicator()
            }
        }

        CustomSwipeRefresh(
            isRefreshing = isRefreshing,
            onRefresh = {  viewModel.getNotifications(startDate,endDate) }
        ) {
            if (state.notifications.isEmpty()) {
                EmptyStateScreen(
                    imageRes = R.drawable.nodata,
                    size = 60.dp,
                    title = stringResource(R.string.no_data),
                )

            } else {


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()

                        .background(FCFCFC),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.notifications, key = { it.id?:"0" }) { notification ->
                        NotificationItem(notification)
                    }
                }

            }

        }
    }
}
