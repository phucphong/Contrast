package com.contrast.Contrast.presentation.features.payment.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.contrast.Contrast.R

import com.contrast.Contrast.presentation.components.text.CustomText

import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack
import com.contrast.Contrast.presentation.features.voucher.ui.Voucher
import com.contrast.Contrast.presentation.features.voucher.ui.vouchers

@Composable
fun PaymentScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Thanh tiêu đề
        CustomTopAppBarTittleBack(
            title = stringResource(R.string.payment_screen_title),
           Color.Black,
             Color.White,
            fontWeight = FontWeight.Bold,
            Color.Black,
            onBackClick = { /* Xử lý nút quay lại */ }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Thông tin số dư
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.rewards),
                    contentDescription = null,
                    modifier = Modifier.size(140.dp, 90.dp),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.balance),
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.weight(1f)
                        )
                        Image(
                            painter = painterResource(R.drawable.arrowright),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp).padding(5.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Text(
                        text = stringResource(R.string.balance_amount),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.balance_time),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.your_discounts), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(
                text = stringResource(R.string.view_all_discounts),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }

        // Danh sách ưu đãi
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.available_rewards, 2),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 16.8.sp,
                        fontFamily = FontFamily(Font(R.font.inter_18pt_bold)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.choose_reward_description),
                    style = TextStyle(
                        fontSize = 10.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0x80000000),
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier.height(120.dp)
                ) {
                    LazyRow {
                        items(vouchers) { voucher ->
                            VoucherItem(voucher)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Barcode thanh toán
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Image(
                    painter = painterResource(R.drawable.hand_holding_phone),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomText(
                    text = stringResource(R.string.barcode_instruction),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(R.drawable.barcode_image),
                contentDescription = null,
                modifier = Modifier.size(200.dp, 60.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            CustomText(
                text = stringResource(R.string.barcode_update),
                FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}


@Composable
fun VoucherItem(voucher: Voucher) {
    Box(
        modifier = Modifier
            .width(270.dp)
            .wrapContentHeight() // Đảm bảo Box không bị ép chiều cao
            .padding(vertical = 8.dp, horizontal = 5.dp)
    ) {
        // Background Image (co giãn theo Column)
        Image(
            painter = painterResource(R.drawable.vourcher_special),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize(), // Ảnh sẽ tự động mở rộng theo Column
            contentScale = ContentScale.FillBounds // Crop ảnh để giữ tỷ lệ tự nhiên
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() // Đảm bảo chiều cao tự động theo nội dung
                .padding(30.dp, 12.dp, 12.dp, 10.dp)
        ) {
            Text(
                text = voucher.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = " ${voucher.expiryDate}",
                fontSize = 12.sp,
                color = Color.Gray
            )

            // Box chứa nút "Áp dụng"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 5.dp, 1.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    Modifier
                        .width(59.dp)
                        .height(15.dp)
                        .background(color = Color(0xFFD91E18), shape = RoundedCornerShape(size = 31.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Áp dụng",
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                            fontWeight = FontWeight(500),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewPaymentScreen() {
    PaymentScreen()
}
