package com.contrast.Contrast.presentation.features.main.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FFD91E18

@Composable
fun EventCard(date: String, month: String, title: String, description: String) {
    Card(
        modifier = Modifier
            .width(260.dp) // 🔥 Định kích thước cố định để hiển thị đẹp trong LazyRow
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)) ,
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)

        ) {
            Box {
                // 🔹 Ảnh sự kiện
                Image(
                    painter = painterResource(R.drawable.store_image), // Thay bằng hình sự kiện thực tế
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp), // 🔥 Giữ đúng tỷ lệ
                    contentScale = ContentScale.Crop // 🔥 Giữ ảnh đúng tỷ lệ, không méo
                )

                // 🔹 Ô ngày tháng
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp)) // 🔥 Viền bo
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp)) // 🔥 Viền mỏng
                        .padding(horizontal = 12.dp, vertical = 10.dp), // 🔥 Canh padding
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = date,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = FFD91E18
                        )
                        Text(
                            text = month,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = FFD91E18
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔹 Tiêu đề sự kiện

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 14.4.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                ), modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            // 🔹 Mô tả sự kiện

            Text(
                text = description,
                style = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7C7C7C),
                )
                , modifier = Modifier.padding(horizontal = 10.dp)
            )




            Text(
                text = "Tìm hiểu thêm ->",
                style = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 16.sp,

                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7C7C7C),
                    textDecoration = TextDecoration.Underline,
                )
                , modifier = Modifier.padding(10.dp)
            )
        }
    }
}

