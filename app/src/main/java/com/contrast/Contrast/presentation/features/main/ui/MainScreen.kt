package com.contrast.Contrast.presentation.features.main.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.main.OnboardingScreen

@Preview(showBackground = true)

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
//@Preview(name = "Galaxy S5", device = "spec:width=360dp,height=640dp")
//@Preview(name = "Phone Mode", device = "spec:width=411dp,height=891dp")
//@Preview(name = "Pixel 5", device = "spec:width=393dp,height=851dp")
//@Preview(name = "Galaxy S20", device = "spec:width=412dp,height=915dp")
//@Preview(name = "OnePlus 9 Pro", device = "spec:width=412dp,height=915dp")
//@Preview(name = "Pixel 4", device = "spec:width=393dp,height=851dp")

@Preview(name = "Tablet Mode", device = "spec:width=1280dp,height=800dp,dpi=240")


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {


    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Đảm bảo toàn bộ nền trắng
        containerColor = Color.White, // Đặt màu nền của Scaffold
        bottomBar = {
            BottomNavigationBar(selectedIndex) { index ->
                selectedIndex = index
            }
        }
    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex
        )
    }
}
@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (selectedIndex) {
            0 -> HomePage()
            1 -> StorePage()
            2 -> ScanPage()
            3 -> LocationPage()
            4 -> MembershipPage()
            else -> HomePage() // Mặc định hiển thị trang Home nếu có lỗi
        }
    }
}


@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf(
        BottomNavItem("Trang chủ", R.drawable.ic_home, true),
        BottomNavItem("Cửa hàng", R.drawable.ic_store, true),
        BottomNavItem("", R.drawable.ic_scan, false, isCenter = true), // Trung tâm
        BottomNavItem("Địa điểm", R.drawable.ic_location, true),
        BottomNavItem("Membership", R.drawable.ic_membership, true)
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp), // Giữ NavigationBar có chiều cao cố định
        containerColor = Color.White, // Nền chính trắng
        tonalElevation = 0.dp // Xóa bóng mờ
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = if (selectedIndex == index) Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFFFD6D6), // Đỏ nhạt trên
                                        Color.White        // Trắng phía dưới
                                    )
                                ) else Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Transparent)
                                ),
                                shape = RoundedCornerShape(0.dp) // Bắt buộc truyền shape vào
                            ),
                        contentAlignment = Alignment.TopCenter
                    )
                    {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxHeight() // 🔹 Đảm bảo nội dung được căn giữa
                        ) {
                            // Thanh đỏ phía trên icon khi item được chọn
                            if (selectedIndex == index) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(1f) // Thanh đỏ chỉ chiếm 50% width item
                                        .height(3.dp)
                                        .background(Color.Red)
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp)) // 🔹 Giữ khoảng cách đồng đều giữa icon và text

                            Image(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label,
                                modifier = Modifier.size(if (item.isCenter) 40.dp else 24.dp),
                                colorFilter = if (!item.isCenter) androidx.compose.ui.graphics.ColorFilter.tint(
                                    if (selectedIndex == index) Color.Red else Color.Gray
                                ) else null // Không áp dụng màu nếu là icon trung tâm
                            )

                            Spacer(modifier = Modifier.height(4.dp)) // 🔹 Giữ text luôn ở vị trí cố định

                            if (item.hasLabel) {
                                Text(
                                    text = item.label,
                                    color = if (selectedIndex == index) Color.Red else Color.Gray,
                                    style = TextStyle(fontSize = 11.sp)
                                )
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // Xóa nền mặc định khi chọn
                ),
                alwaysShowLabel = true
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val hasLabel: Boolean,
    val isCenter: Boolean = false // Thêm biến để xác định item trung tâm
)

