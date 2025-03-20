package com.contrast.Contrast.presentation.features.main.location.ui


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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Tab
import androidx.compose.material.TabRow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.main.home.ui.StoreCard
import com.contrast.Contrast.presentation.features.main.ui.BottomNavigationBar

@Preview(showBackground = true)
@Composable
fun StoreListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        StoreHeader()

        // Tabs
        StoreTabs()

        // Danh sách cửa hàng
        StoreList()


    }
}

// 🟥 Header - Hiển thị vị trí
@Composable
fun StoreHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = "Location",
                modifier = Modifier.size(30.dp).padding(5.dp)
            )

            Column {
                Text(
                    text = "Vị trí hiện tại",
                    fontSize = 12.sp,
                    color = Color.White
                )
                Text(
                    text = "Cầu Giấy, Hà Nội",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// 🟡 Tabs - "Danh sách" & "Gần tôi"
@Composable
fun StoreTabs() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Danh sách", "Gần tôi")

    TabRow(
        selectedTabIndex = selectedTab,
        backgroundColor = Color.White,
        contentColor = Color.Red
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = {
                    Text(
                        text = title,
                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}

// 📌 Danh sách cửa hàng
@Composable
fun StoreList() {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),

        ) {
        items(listOf(
            Pair("3,5 km", "Contrast Văn Chương"),
            Pair("4,2 km", "Contrast Tô Hiệu"),
            Pair("2,1 km", "Contrast Trần Duy Hưng")
        )) { (distance, name) ->
            StoreCard( name, "Địa chỉ chi tiết ở đây...")
        }}

}

// 📌 Item Cửa Hàng
@Composable
fun StoreItem(name: String, address: String) {
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


                    androidx.compose.material.Text(
                        text = name,
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                    androidx.compose.material.Text(
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

// 🏠 Bottom Navigation Bar



