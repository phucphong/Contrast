package com.contrast.Contrast.presentation.features.affiliate

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.contrast.Contrast.presentation.navigator.BottomNavigationBar
import com.contrast.Contrast.presentation.navigator.navgraph.RootNavigationGraph
import com.contrast.Contrast.presentation.navigator.router.routes.AccountRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.AffiliateRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.CategoryProductRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.MainRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.NewsRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.ProductRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.VideoRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AffiliateMainScreen(
    navHostController: NavHostController,
    idProductFromShare: String = "0",
    idUnitFromShare: String = "0",
    introducerId: String = "0",
) {
    val navController = rememberNavController()
    val hasNavigatedToProduct = remember { mutableStateOf(false) }

    // Theo dõi route hiện tại
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Các route hiển thị bottom nav
    val bottomNavRoutes = listOf(
        AffiliateRoutes.AffiliateHome.route,
        CategoryProductRoutes.CategoryProduct.route,
        VideoRoutes.Videos.route,
        NewsRoutes.NewsList.route,
        AccountRoutes.Account.route
    )
    val context = LocalContext.current
    val backPressedOnce = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = currentRoute in bottomNavRoutes) {
        if (backPressedOnce.value) {
            // Thoát app
            (context as? android.app.Activity)?.finish()
        } else {
            backPressedOnce.value = true
            Toast.makeText(context, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show()
            coroutineScope.launch {
                delay(2000) // reset sau 2 giây
                backPressedOnce.value = false
            }
        }
    }
    // Đồng bộ selectedIndex với currentRoute
    val selectedIndex = bottomNavRoutes.indexOf(currentRoute).coerceAtLeast(0)

    // Điều hướng từ link chia sẻ (chỉ thực hiện 1 lần)
    LaunchedEffect(Unit) {
        if (idProductFromShare != "0" && !hasNavigatedToProduct.value) {
            hasNavigatedToProduct.value = true
            navController.navigate(
                ProductRoutes.ProductDetail.withArgs(
                    id = idProductFromShare,
                    idUnit = idUnitFromShare,
                    introducerId = introducerId
                )
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            if (currentRoute in bottomNavRoutes) {
                BottomNavigationBar(selectedIndex) { index ->
                    val targetRoute = bottomNavRoutes.getOrNull(index)
                    if (targetRoute != null && targetRoute != currentRoute) {
                        navController.navigate(targetRoute) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    ) {
        RootNavigationGraph( navController) // ✅ đúng
    }
}
