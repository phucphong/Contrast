package com.contrast.Contrast.presentation.features.affiliate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            RootNavigationGraph(
                navController = navController
            )
        }
    }
}
