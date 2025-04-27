package com.contrast.Contrast.presentation.features.cart.ui
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle
import com.contrast.Contrast.presentation.features.product.detail.ui.QuantitySelector
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.UltraLightGray

@Composable
fun CartScreen(navHostController: NavHostController) {
    var isAllSelected by remember { mutableStateOf(true) }
    var note by remember { mutableStateOf("") }
    val cartItems = listOf(
        Triple("Chăm sóc da cơ bản", "250.000", R.drawable.noimagevetical), // Đổi thành ảnh sản phẩm
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)) // nền xám nhạt
    ) {
        // Header
        CartHeader(
            onBackPress = { navHostController.popBackStack() },
            onBackHome = {

        })

CustomDividerColor()
        // Địa chỉ nhận hàng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(UltraLightGray)
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
                .background(Color.White)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CheckBoxColor(checked = isAllSelected, padding=6.dp,onCheckedChange = { isAllSelected = it })
                Spacer(modifier = Modifier.width(6.dp))
                Text(stringResource(R.string.cart_select_all))
            }
            Text(
                text = stringResource(R.string.cart_delete_all),
                color = Color.Red,
                modifier = Modifier.clickable { /* TODO: delete all */ }
            )
        }

        CustomDividerColor()

        // Danh sách sản phẩm
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFFAFAFA))
                .padding(12.dp)
        ) {
            items(cartItems.size) { index ->
                val item = cartItems[index]
                CartItemRow(
                    itemName = item.first,
                    price = item.second,
                    imageResId = item.third
                )
                Spacer(modifier = Modifier.height(12.dp))
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


            Image(painter = painterResource(R.drawable.edit),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Red),
                modifier = Modifier.size(25.dp).padding(5.dp))
            Spacer(modifier = Modifier.width(6.dp))
            BasicTextField(
                value = note,

                onValueChange = { note = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                decorationBox = { innerTextField ->
                    if (note.isEmpty()) {
                        Text(
                            text = stringResource(R.string.cart_item_note),
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            )
        }



        // Tổng tiền + nút
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)

        ) {
            RowAmount("TỔNG TIỀN:", "đ250.000", isBold = true)
            RowAmount("CHIẾT KHẤU:", "đ0")
            RowAmount("THÀNH TIỀN:", "đ250.000", isBold = true)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* TODO: tư vấn thêm */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(0.dp) ,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(text = "CẦN TƯ VẤN THÊM", color = Color.White)
                }

                Button(
                    onClick = { /* TODO: thanh toán */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(0.dp) ,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF009688))
                ) {
                    Text(text = "THANH TOÁN", color = Color.White)
                }
            }
        }
    }
}
