package com.contrast.Contrast.presentation.features.voucher.detail

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack
import com.contrast.Contrast.presentation.theme.FCFCFC
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.FFD91E18


@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun VoucherDetailScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FCFCFC),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        CustomTopAppBarTittleBack(
            title = stringResource(id = R.string.voucher_detail),
            FFD91E18,
            FCFCFC,
            onBackClick = { }
        )




        Card(
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(12.dp, 12.dp,12.dp,60.dp)
                .clip(RoundedCornerShape(10.dp)), // Cắt viền dưới
            elevation = 8.dp // giảm elevation để tránh shadow rõ
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight() // Đảm bảo Box không bị ép chiều cao
                    .padding(10.dp,10.dp,10.dp,0.dp)
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    // voucher
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight() // Đảm bảo Box không bị ép chiều cao
                            .padding(15.dp)
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
                                .padding(40.dp, 12.dp, 12.dp, 10.dp)
                        ) {
                            Text(
//                    text = voucher.title,
                                text = "Giảm giá đ100,000 cho hóa đơn thứ 3 trong tuần",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
//                    text = " ${voucher.expiryDate}",
                                text = " Hết hạn vào 31/01/2026",
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

                                Row(verticalAlignment = Alignment.Bottom) {

                                    Image(
                                        painter = painterResource(R.drawable.cup_1),
                                        contentDescription = "cup icon",
                                        modifier = Modifier.size(25.dp)
                                    )

                                    Text(
                                        text = "100",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = FF151515,
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .padding(start = 5.dp, end = 10.dp)
                                    )
                                }


                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Thời hạn voucher
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 5.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.timer),
                            contentDescription = "Time",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(start = 8.dp),


                            )
                        Text(
                            text = "Thời hạn voucher:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Text(
                        text = "31/01/2026 23:59",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = FFD91E18,
                        modifier = Modifier.padding(start = 10.dp, bottom = 16.dp)
                    )


                    // Chi tiết voucher
                    Text(
                        text = "Chi tiết voucher",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "Discount - Giảm giá 20,000đ cho hóa đơn tiếp theo",
                        fontSize = 14.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Điều kiện sử dụng
                    Text(
                        text = "Điều kiện sử dụng",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Column(modifier = Modifier.padding(start = 8.dp)) {
                        listOf(
                            "Voucher không có giá trị quy đổi thành tiền mặt",
                            "Voucher được áp dụng tại toàn bộ hệ thống cửa hàng Contrast Coffee",
                            "Voucher chỉ áp dụng với đơn hàng tại cửa hàng"
                        ).forEach { condition ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.back),
                                    contentDescription = "Bullet",
                                    tint = Color.Black
                                )
                                Text(
                                    text = condition,
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }


                }


                // Nút Áp dụng
                Button(
                    onClick = { /* Xử lý áp dụng voucher */ },
                    colors = ButtonDefaults.buttonColors(FFD91E18),
                    shape = RoundedCornerShape(10.dp),

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp).padding( horizontal = 10.dp)
                ) {
                    Text(
                        text = "Áp dụng",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.inter_18pt_bold)),
                        color = Color.White,

                    )
                }

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.15f).padding(top = 50.dp),
                    painter = painterResource(R.drawable.footter_paymen_commit),
                    contentDescription = ""

                )

            }
        }

    }
}
