package com.contrast.Contrast.presentation.features.order.detail


import android.content.res.Configuration
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog

import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle
import com.contrast.Contrast.presentation.features.order.detail.item.OrderItemViewDetail
import com.contrast.Contrast.presentation.features.order.detail.item.FooterRowItem

import com.contrast.Contrast.presentation.features.order.viewmodel.OrderDetailViewModel

import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.contrast.Contrast.presentation.theme.TealGreen
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderDetailScreen(
    navHostController: NavHostController,
    id: String,
    type: String,
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(100)

        viewModel.getOderById(type, id)


    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomBackTitle(title = stringResource(R.string.order_detail_title),
            painter = painterResource(id = R.drawable.quaylai),
            tint = TealGreen,
            textColor = Color.Black,
            onBackPress = { navHostController.popBackStack() })


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // 🔹 Địa chỉ nhận hàng
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.location),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(TealGreen),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(10.dp)
                )
                Text(
                    text = stringResource(R.string.delivery_address),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 14.sp
                )


            }

            Column(modifier = Modifier.padding(start = 20.dp, end = 10.dp)) {

                Text(
                    text = state.order?.tenkhachhang ?: "",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = state.order?.diachigiaohang ?: "",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = stringResource(R.string.waiting_confirm),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(FAFAFA)
            ) {


                if (state.isLoading) {
                    item {
                        CustomCircularProgressIndicatorDialog(show = isLoading,
                            onDismissRequest = { isLoading = false })
                    }
                }else{
                    if (state.orders.isEmpty()) {
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

                        val rows = state.orders.chunked(1)

                        items(rows, key = { row -> row.firstOrNull()?.id ?: "row" }) { row ->
                            val order = row.firstOrNull()

                            if (order != null) {

                                val fullUrl = "${state.domain}${order.hinhanhtxt}"
                                var quantity = order.soluong ?: 0.0
                                if (quantity == 0.0) {
                                    quantity = 1.0
                                }

                                val ck = order.ck ?: 0.0
                                val ckMoney = order.cksotien ?: 0.0
                                val price = order.dongia ?: 0.0

                                var moneyDisCount = 0.0
                                if (ck > 0) {
                                    moneyDisCount = (quantity * price * ck) / 100
                                }
                                if (ckMoney > 0) {
                                    moneyDisCount = ckMoney
                                }
                                var totalAmount = 0.0
                                totalAmount = price - moneyDisCount

                                OrderItemViewDetail(
                                    fullUrl = fullUrl,
                                    orderKey = order.tensanpham ?: "",
                                    totalAmount = totalAmount.formatDouble(),
                                    quantity = quantity.formatDouble()
                                )


                            }
                        }

                    }

                }

            }

            Spacer(modifier = Modifier.height(16.dp))


            // 🔹 Ghi chú đơn hàng + tổng kết

            Row {
                Image(
                    painter = painterResource(R.drawable.note_pad), contentDescription = "",

                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                )
                Text(
                    text = state.order?.ghichu ?: "",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            FooterRowItem(
                label = stringResource(R.string.total_price),
                value = state.order?.tongtientruocchietkhau ?: 0.0
            )
            if (state.typeAccount == "khachhang") {
                FooterRowItem(
                    label = stringResource(R.string.discount),
                    value = state.order?.tongtienchietkhau ?: 0.0
                )
            }

            FooterRowItem(
                label = stringResource(R.string.final_price),
                value = state.order?.tongtiensauchietkhau ?: 0.0
            )

        }
    }
}


