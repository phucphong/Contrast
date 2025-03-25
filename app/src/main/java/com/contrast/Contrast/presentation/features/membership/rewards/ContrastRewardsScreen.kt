package com.contrast.Contrast.presentation.features.membership.rewards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun ContrastRewardsScreen() {
    val menuItems = listOf(
        "Quyền lợi hạng thành viên",
        "Cách xếp hạng điểm",
        "Voucher của bạn",
        "Đổi cup lấy voucher",
        "Lịch sử đổi Cup"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(bottom = 80.dp) // chừa chỗ cho bottom nav nếu có
    ) {
        // 🔺 Header đỏ
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE40000))
                    .padding(top = 40.dp, bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "CONTRAST\nREWARDS",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // 🧭 Card cấp bậc
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Trailblazer",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                        Text(
                            text = "Chinh phục và nhận thưởng!",
                            fontSize = 14.sp
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.rewards),
                        contentDescription = "Illustration",
                        modifier = Modifier.size(80.dp)
                    )
                }
            }
        }

        // 🏆 Card điểm số
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "40 🏆",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = { /* TODO */ },
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text("Đổi score")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "• Tích điểm mỗi lần mua.\n• Điểm có hiệu lực 3 tháng kể từ ngày tích.",
                        fontSize = 13.sp
                    )
                }
            }
        }

        // 📋 Menu danh sách
        items(menuItems) { item ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { /* TODO: Navigate */ }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
                Divider()
            }
        }
    }
}
