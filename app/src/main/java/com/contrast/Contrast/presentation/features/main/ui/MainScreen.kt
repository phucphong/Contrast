package com.contrast.Contrast.presentation.features.main.ui



import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.account.ui.AccountScreen
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliateModel
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.navigator.AppNavHost
import com.contrast.Contrast.presentation.features.store.ui.StoreListScreen
import com.contrast.Contrast.presentation.features.membership.rewards.RewardsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(selectedIndex) { index ->
                selectedIndex = index
                when (index) {
                    0 -> navController.navigate("home")
                    1 -> navController.navigate("location")
                    2 -> navController.navigate("storeList")
                    3 -> navController.navigate("membership")
                    4 -> navController.navigate("account")
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavHost(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf(
        BottomNavItem("Trang chủ", R.drawable.ic_home, true),
        BottomNavItem("Địa điểm", R.drawable.ic_location, true),
        BottomNavItem("Cửa hàng", R.drawable.ic_store, true),
        BottomNavItem("Membership", R.drawable.ic_membership, true),
        BottomNavItem("Contrast Box", R.drawable.contrast_box, true),
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        containerColor = Color.White,
        tonalElevation = 0.dp
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
                                        Color(0xFFFFD6D6),
                                        Color.White
                                    )
                                ) else Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Transparent)
                                ),
                                shape = RoundedCornerShape(0.dp)
                            ),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            if (selectedIndex == index) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .height(3.dp)
                                        .background(Color.Red)
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Image(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label,
                                modifier = Modifier.size(if (item.isCenter) 40.dp else 24.dp),
                                colorFilter = if (!item.isCenter) androidx.compose.ui.graphics.ColorFilter.tint(
                                    if (selectedIndex == index) Color.Red else Color.Gray
                                ) else null
                            )

                            Spacer(modifier = Modifier.height(4.dp))

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
                    indicatorColor = Color.Transparent
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
    val isCenter: Boolean = false
)