package com.contrast.Contrast.presentation.features.main.ui
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.slider.ImageSlider
import com.contrast.Contrast.presentation.components.text.CustomText

@Preview(showBackground = true)

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Galaxy S5", device = "spec:width=360dp,height=640dp")
@Preview(name = "Tablet Mode", device = "spec:width=1280dp,height=800dp,dpi=240")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCFCFC))
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 HEADER */
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.avatar1),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    CustomText("Contrast xin chào 👋", fontSize = 12.sp, color = Color.Gray)
                    CustomText("Nguyễn Phúc Phong", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            Row {
                IconButton(onClick = { /* TODO: Notifications */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notification),
                        contentDescription = "Thông báo"
                    )
                }
                IconButton(onClick = { /* TODO: Wallet */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wallet),
                        contentDescription = "Nạp tiền"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 SỐ DƯ */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(2f) // 🔹 Chiếm 2 phần
                    .background(Color(0xFFFCFCFC))
            ) {
                CustomText("Số dư", fontSize = 14.sp, color = Color.Gray)
                CustomText("đ 8,656.60 >", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }



            Button(
                onClick = { /* TODO: Nạp tiền */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f) // 🔹 Chiếm 1 phần
            ) {
                Text("+ Nạp tiền", color = Color.White)
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 BANNER QUẢNG CÁO */

        ImageSlider()

        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 SỰ KIỆN ĐANG DIỄN RA */
        CustomText(stringResource(id = R.string.events_happening), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            EventCard("10 TH6", "Tặng Sticker chúc mừng Quốc Khánh 2-9")
            EventCard("15 TH6", "Đổi bạn cùng tiền")
        }

        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 DANH SÁCH CỬA HÀNG */
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CustomText(stringResource(id = R.string.store_list), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.see_all)),
                onClick = { /* TODO: Xem tất cả cửa hàng */ },
                style = TextStyle(color = Color.Red, fontSize = 14.sp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            StoreCard("3,5 km", "Contrast Văn Chương", "264 ngõ Văn Chương, Khâm Thiên, Hà Nội")
            StoreCard("3,5 km", "Contrast Tô Hiệu", "217 Tô Hiệu, Hà Đông, Hà Nội")
        }

        Spacer(modifier = Modifier.height(16.dp))

        /** 🔹 NÚT PHẢN HỒI */
        Button(
            onClick = { /* TODO: Phản hồi */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFCFCFC)), // Màu nền #FCFCFC
            shape = RoundedCornerShape(8.dp), // Bo góc
            elevation = ButtonDefaults.buttonElevation( // ✅ Thêm shadow
                defaultElevation = 6.dp, // Độ cao bình thường
                pressedElevation = 2.dp, // Khi nhấn giảm shadow
                focusedElevation = 8.dp, // Khi focus
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp) // Đặt chiều cao cố định
                .padding(horizontal = 80.dp)
                .shadow(6.dp, shape = RoundedCornerShape(8.dp)) // ✅ Thêm shadow tùy chỉnh
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Vòng tròn đỏ chứa icon
                Box(
                    modifier = Modifier
                        .size(40.dp) // Kích thước vòng tròn
                        .background(Color.Red, shape = CircleShape), // Màu đỏ + hình tròn
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.feedback), // Thay bằng ảnh từ drawable
                        contentDescription = "Feedback Icon",
                        modifier = Modifier.size(24.dp) // Kích thước ảnh
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Văn bản "Phản hồi"
                Text(
                    text = stringResource(id = R.string.feedback),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun EventCard(date: String, title: String) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .background(Color.Red, shape = RoundedCornerShape(4.dp))
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(date, fontSize = 12.sp, color = Color.White, textAlign = TextAlign.Center)
        }
        Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
        Text("Tìm hiểu thêm →", fontSize = 12.sp, color = Color.Blue)
    }
}

@Composable
fun StoreCard(distance: String, name: String, address: String) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .background(Color.Gray, shape = RoundedCornerShape(4.dp))
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(distance, fontSize = 12.sp, color = Color.White)
        }
        Text(name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(address, fontSize = 12.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomePage() {
    HomePage()
}
