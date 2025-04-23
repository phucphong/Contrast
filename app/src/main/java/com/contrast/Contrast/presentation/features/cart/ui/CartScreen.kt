package com.contrast.Contrast.presentation.features.cart.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.features.product.detail.ui.QuantitySelector

@Composable
fun CartScreen() {
    var isAllSelected by remember { mutableStateOf(true) }
    var quantity by remember { mutableStateOf(1) }

    Column(modifier = Modifier.fillMaxSize()) {

        // Header
        CartHeader()

        // Địa chỉ nhận hàng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF00BCD4))
            Spacer(modifier = Modifier.width(6.dp))
            Text(stringResource(R.string.cart_delivery_address), fontWeight = FontWeight.Medium)
        }


        // Chọn tất cả / Xóa tất cả
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CheckBoxColor (checked = isAllSelected, onCheckedChange = { isAllSelected = it })
                Text(stringResource(R.string.cart_select_all))
            }
            Text(
                text = stringResource(R.string.cart_delete_all),
                color = Color.Red,
                modifier = Modifier.clickable { /* TODO */ }
            )
        }

        val cartItems = listOf(
            Triple("Chăm sóc da cơ bản", "250.000", R.drawable.scan),
            Triple("Sản phẩm khác", "180.000", R.drawable.settings)
        )

        LazyColumn {
            items(cartItems.size) { index ->
                val item = cartItems[index]
                var checked by remember { mutableStateOf(true) }
                var quantity by remember { mutableStateOf(1) }

                CartItemRow(
                    itemName = item.first,
                    price = item.second,
                    imageResId = item.third,
                    isChecked = checked,
                    quantity = quantity,
                    onCheckedChange = { checked = it },
                    onQuantityChange = { quantity = it }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Ghi chú đơn hàng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFFFF4081))
            Spacer(modifier = Modifier.width(6.dp))
            Text(stringResource(R.string.cart_item_note))
        }

        Divider()

        // Tổng tiền
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)) {
            RowAmount("TỔNG TIỀN:", "đ250.000")
            RowAmount("CHIẾT KHẤU:", "đ0")
            RowAmount("THÀNH TIỀN:", "đ250.000", isBold = true)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Nút tư vấn và thanh toán
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* tư vấn */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(stringResource(R.string.cart_need_consultation), color = Color.White)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { /* thanh toán */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF009688))
            ) {
                Text(stringResource(R.string.cart_checkout), color = Color.White)
            }
        }
    }
}
