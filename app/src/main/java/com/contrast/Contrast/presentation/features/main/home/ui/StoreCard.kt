package com.contrast.Contrast.presentation.features.main.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Preview(showBackground = true)@Composable
fun StoreCard(name: String, address: String) {
    Card(
        modifier = Modifier
            .width(220.dp) // 🔥 Kích thước chuẩn theo thiết kế
            .height(160.dp), // 🔥 Chiều cao hợp lý
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Box {
            // 🔹 Hình ảnh cửa hàng
            Image(
                painter = painterResource(R.drawable.store_image),
                contentDescription = "Store Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // 🔥 Giữ tỷ lệ ảnh, không bị méo
            )

            // 🔹 Icon điều hướng (góc trên phải)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(36.dp)
                    .background(Color.Red, shape = RoundedCornerShape(8.dp)), // 🔥 Viền bo tròn
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.navigation),
                    contentDescription = "Navigation Icon",
                    modifier = Modifier.size(20.dp) // 🔥 Icon nhỏ gọn đúng thiết kế
                )
            }

            // 🔹 Overlay chứa thông tin cửa hàng (nằm dưới ảnh)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.6f)) // 🔥 Nền mờ 60% đen
                    .padding(10.dp)
            ) {
                Column {


                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                    Text(
                        text = address,
                        style = TextStyle(
                            fontSize = 10.sp,
                            lineHeight = 15.sp,
                            fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFD7D7D7),
                        )
                    )
                }
            }
        }
    }
}
