package com.contrast.Contrast.presentation.navigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.contrast.Contrast.presentation.navigator.graph.affiliateNavGraph

import com.contrast.Contrast.presentation.navigator.graph.reviewNavGraph
import com.contrast.Contrast.presentation.navigator.router.NavRoutes


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController,
               ) {
    NavHost(navController, startDestination = NavRoutes.AffiliateRoot.route) {
        affiliateNavGraph(navController)
        reviewNavGraph(navController)// dùng chung viewModel để lấy dữ liệu ảnh

    }
}
