package com.contrast.Contrast.presentation.features.cart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.presentation.components.EmptyStateScreen

import com.contrast.Contrast.presentation.components.alertDialog.CustomAlertOkCancelDialog
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.theme.FFFAFAFA
import com.contrast.Contrast.presentation.theme.FFFF5722
import com.contrast.Contrast.presentation.theme.TealGreen

import com.contrast.Contrast.presentation.theme.UltraLightGray

@Composable
fun CartScreen(
    navHostController: NavHostController,
    viewModel: CartViewModel = hiltViewModel(),
) {

    val totalValue by viewModel.totalValue.collectAsState()
    val totalIntoMoney by viewModel.totalIntoMoney.collectAsState()
    val amountMoneyDiscount by viewModel.amountMoneyDiscount.collectAsState()
    val isAllSelected by viewModel.isAllSelected.collectAsState()


    var isDeleteAll by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val carts by viewModel.carts.collectAsState()
    val domain by viewModel.domain.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.onCheckedChangeAll(carts, isAllSelected)
    }
    LaunchedEffect(Unit) {

        viewModel.getCarts(true)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FFFAFAFA) // nền xám nhạt
    ) {
        // Header
        CartHeader(onBackPress = { navHostController.popBackStack() }, onBackHome = {

        })


        // Địa chỉ nhận hàng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 6.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = TealGreen)
            Spacer(modifier = Modifier.width(6.dp))


            BasicTextField(value = address,

                onValueChange = { address = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (address.isEmpty()) {
                        Text(
                            text = stringResource(R.string.cart_delivery_address),
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                })
        }


        // Danh sách sản phẩm
        if (carts.isNotEmpty()) {

            // Chọn tất cả / Xóa tất cả
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFAFAFA))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CheckBoxColor(checked = isAllSelected,
                        padding = 8.dp,
                        size = 15.dp,
                        backgroundChecked = TealGreen,
                        backgroundUnChecked = TealGreen,
                        onCheckedChange = { isChecked ->
                            viewModel.isAllSelected(carts, isChecked)
                        })
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(stringResource(R.string.cart_select_all),
                        color = TealGreen,
                        modifier = Modifier.noRippleClickableComposable {
                            viewModel.isAllSelected(carts, !isAllSelected)
                        }

                    )
                }
                Text(text = stringResource(R.string.cart_delete_all),
                    color = Color.Red,
                    modifier = Modifier.noRippleClickableComposable { isDeleteAll = true })
            }


            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFFAFAFA))
                    .padding(horizontal = 10.dp),
            ) {
                items(carts) { cart ->

                    CartItemRow(
                        cart = cart,
                        domain = domain,
                        onCheckedChange = {},
                        increaseQuantity = { viewModel.increaseCartQuantity(cart, "update") },
                        onQuantityChange = { viewModel.onQuantityChange(cart, "update", it) },
                        decreaseQuantity = { viewModel.decreaseCartQuantity(cart, "update") },
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        } else {
            Box(modifier = Modifier.weight(1f)) {

                EmptyStateScreen(
                    imageRes = R.drawable.emptycart,
                    size = 90.dp,
                    title = stringResource(R.string.empty_cart),
                    background = FFFAFAFA,
                    modifier = Modifier.padding(40.dp)
                )
            }
        }

        // Ghi chú đơn hàng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Image(
                painter = painterResource(R.drawable.note_pad), contentDescription = null,

                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            BasicTextField(value = note,

                onValueChange = { note = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (note.isEmpty()) {
                        Text(
                            text = stringResource(R.string.cart_item_note), color = Color.Gray
                        )
                    }
                    innerTextField()
                })
        }


        // Tổng tiền + nút
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)

        ) {
            RowAmount(
                stringResource(R.string.cart_total_amount), "", totalValue.formatDouble(), true
            )
            RowAmount(
                stringResource(R.string.cart_discount),
                "-",
                amountMoneyDiscount.formatDouble(),
                textColor = FFFF5722
            )
            RowAmount(
                stringResource(R.string.cart_final_amount), "", totalIntoMoney.formatDouble(), true
            )



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* TODO: tư vấn thêm */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(
                        text = stringResource(R.string.cart_need_consultation), color = Color.White
                    )
                }

                Button(
                    onClick = { /* TODO: thanh toán */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF009688))
                ) {
                    Text(text = stringResource(R.string.cart_checkout), color = Color.White)
                }
            }
        }
    }

    if (isDeleteAll) {
        CustomAlertOkCancelDialog(
            message = stringResource(R.string.you_want_to_deleta_all_cart),
            onOk = {
                isDeleteAll = false
                viewModel.deleteAll(carts)
            },
            onDismiss = { isDeleteAll = false },
        )

    }
}
