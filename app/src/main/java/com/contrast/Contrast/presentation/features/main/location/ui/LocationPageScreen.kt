package com.contrast.Contrast.presentation.features.main.location.ui


import androidx.compose.foundation.background
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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Tab
import androidx.compose.material.TabRow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.features.main.home.ui.StoreCard
import com.contrast.Contrast.presentation.theme.AFFFFFF
import com.contrast.Contrast.presentation.theme.B2FFFFFF
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.FFD91E18

@Preview(showBackground = true)
@Composable
fun LocationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FFD91E18)
    ) {
        // Header
        StoreHeader()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)) // Bo tròn góc trên
        ) {
            // Tabs
            StoreTabs()
            // Danh sách cửa hàng
            StoreList()
        }


    }
}

// 🟥 Header - Hiển thị vị trí
@Composable
fun StoreHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FFD91E18)
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {


            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(36.dp)
                    .background(AFFFFFF, shape = RoundedCornerShape(8.dp)), // 🔥 Viền bo tròn
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.map),
                    contentDescription = "Navigation Icon",
                    modifier = Modifier.size(20.dp) // 🔥 Icon nhỏ gọn đúng thiết kế
                )
            }

            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                CustomText(
                    text = stringResource(R.string.current_location),
                    fontSize = 12.sp,
                    color = Color.White
                )
                CustomText(
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
    val tabs = listOf(stringResource(R.string.store_list), stringResource(R.string.near_me))

    TabRow(
        selectedTabIndex = selectedTab,
        backgroundColor = Color.White,
        contentColor = FFD91E18
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = {
                    Text(
                        text = title,
                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                            fontWeight = if (selectedTab == index) FontWeight(500) else FontWeight(
                                400
                            ),
                            color = Color(0xFF151515),


                            textAlign = TextAlign.Center,
                        )


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
        items(
            listOf(
                Pair("3,5 km", "Contrast Văn Chương"),
                Pair("4,2 km", "Contrast Tô Hiệu"),
                Pair("2,1 km", "Contrast Trần Duy Hưng")
            )
        ) { (distance, name) ->
            StoreItem(name, "Địa chỉ chi tiết ở đây...")
        }
    }

}

// 📌 Item Cửa Hàng
@Composable
fun StoreItem(name: String, address: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // 🔥 Kích thước chuẩn theo thiết kế
            .height(219.dp)
            .padding(10.dp) , // 🔥 Chiều cao hợp lý
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
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)


            )
            {
                Box(
                    Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(20.dp,8.dp,8.dp,8.dp)
                        .background(
                            color =B2FFFFFF,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                       ,
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "3,5 km",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                            fontWeight = FontWeight(500),
                            color = FF151515,
                        ),
                        modifier = Modifier
                            .padding(9.dp)


                    )
                }

                Box(
                    Modifier.weight(1f)
                )
//  giở hàng
                Box(
                    modifier = Modifier

                        .padding(8.dp)
                        .size(36.dp)
                        .background(FFD91E18, shape = RoundedCornerShape(8.dp)), // 🔥 Viền bo tròn
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.bag),
                        contentDescription = "bag Icon",
                        modifier = Modifier.size(20.dp) // 🔥 Icon nhỏ gọn đúng thiết kế
                    )
                }

                // vị trí


                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(36.dp)
                        .background(FFD91E18, shape = RoundedCornerShape(8.dp)), // 🔥 Viền bo tròn
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.navigation),
                        contentDescription = "Navigation Icon",
                        modifier = Modifier.size(20.dp) // 🔥 Icon nhỏ gọn đúng thiết kế
                    )
                }

            }

            // 🔹 Overlay chứa thông tin cửa hàng (nằm dưới ảnh)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .fillMaxHeight(1f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.Transparent),
                            startY = Float.POSITIVE_INFINITY, // Bắt đầu từ đáy
                            endY = 0f // Kết thúc ở giữa view
                        )
                    )
                    .padding(10.dp)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)



                ){


                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFFFFFFF),

                            ),
                        modifier = Modifier.padding(5.dp)
                    )
                    Row(
                        modifier = Modifier.padding(5.dp,0.dp,5.dp,5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.location),
                            contentDescription = "map",
                            modifier = Modifier.size(15.dp)
                        )

                        Text(
                            text = address,
                            style = TextStyle(
                                fontSize = 10.sp,
                                lineHeight = 15.sp,
                                fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFD7D7D7),
                            ),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}

// 🏠 Bottom Navigation Bar



