package com.contrast.Contrast.presentation.features.order.detail


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog

import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle
import com.contrast.Contrast.presentation.features.order.detail.item.OrderItemViewDetail

import com.contrast.Contrast.presentation.features.order.viewmodel.OrderDetailViewModel
import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes
import com.contrast.Contrast.presentation.navigator.routers.CartRoutes
import com.contrast.Contrast.presentation.navigator.routers.NotificationRoutes
import com.contrast.Contrast.presentation.navigator.routers.ProductRoutes
import com.contrast.Contrast.presentation.navigator.routers.ServiceRequestRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderDetailScreen(
    navHostController: NavHostController,
    ido: String,
    type: String,
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val listState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(100)

        viewModel.getOderById(type, ido)


    }






    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomBackTitle(title = stringResource(R.string.order_detail_title),
            painter = painterResource(id = R.drawable.quaylai),
            onBackPress = { navHostController.popBackStack() })


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // 🔹 Địa chỉ nhận hàng
            Row {
                Image(
                    painter = painterResource(R.drawable.location),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(TealGreen),
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                )

                Column(modifier = Modifier.padding(horizontal = 10.dp)) {

                    Text(
                        text = stringResource(R.string.delivery_address),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                    )
                    Text(
                        text = state.order?.ngaytaotxt ?: "", color = Color.Black, fontSize = 14.sp
                    )
                    Text(
                        text = state.order?.diachigiaohang ?: "",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = stringResource(R.string.waiting_confirm),
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }

            }



            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FAFAFA)
            ) {


                if (state.isLoading) {
                    item {
                        CustomCircularProgressIndicatorDialog(
                            show = isLoading,
                            onDismissRequest = { isLoading = false })
                    }
                }
                if (state.pagedOrders.isEmpty()) {
                    item {
                        EmptyStateScreen(
                            imageRes = R.drawable.emptycart,
                            size = 90.dp,
                            title = stringResource(R.string.empty_oder),
                            background = FFFAFAFA,
                            modifier = Modifier.padding(40.dp)
                        )
                    }

                } else {

                    val rows = state.pagedOrders.chunked(1)

                    items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                        val order = row.firstOrNull()

                        if (order != null) {
                            val totalAmount = order.tongtien ?: 0.0
                            val fullUrl = "${state.domain}${order.hinhanhtxt}"
                            var createDate = ""
                            if (order.ngaytao != null) {
                                createDate = order.ngaytao ?: ""
                            }
                            if (order.ngaytaotxt != null) {
                                createDate = order.ngaytaotxt ?: ""
                            }

                            OrderItemViewDetail(

                                fullUrl = fullUrl,
                                orderKey = "${stringResource(R.string.order)}: ${order.ma}",
                                totalAmount = totalAmount.toString(),
                                dateOrder = "${stringResource(R.string.createDate)}: $createDate"
                            )

                        }
                    }

                }

            }

            Spacer(modifier = Modifier.height(16.dp))


            // 🔹 Ghi chú đơn hàng + tổng kết

          Row {
              Image(
              painter = painterResource(R.drawable.note_pad),
              contentDescription = "",
              colorFilter = ColorFilter.tint(TealGreen),
              modifier = Modifier
                  .size(30.dp)
                  .padding(5.dp)
          )
              Text(
                  text = state.order_note,
                  color = Color.Gray,
                  fontSize = 14.sp,
                  modifier = Modifier.padding(vertical = 10.dp)
              ) }

            RowItem(
                label = stringResource(R.string.total_price), value = state.order?.tongtien ?: 0.0
            )
            RowItem(
                label = stringResource(R.string.discount), value = state.order?.tongtienconno ?: 0.0
            )
            RowItem(
                label = stringResource(R.string.final_price), value = state.order?.tongtien ?: 0.0
            )

        }
    }
}

@Composable
fun RowItem(label: String, value: Double?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label.uppercase(), fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Text(
            text = "₫${value?.toLong()?.toString() ?: "0"}",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = Color.Black
        )
    }
}
