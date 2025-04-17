package com.contrast.Contrast.presentation.navigator.graph

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.affiliate.home.HomeAffiliatePage
import com.contrast.Contrast.presentation.features.video.VideoScreen
import com.contrast.Contrast.presentation.navigator.NavRoutes

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.affiliateNavGraph(navController: NavHostController) {
    navigation(
        startDestination = NavRoutes.AffiliateHome.route,
        route = NavRoutes.AffiliateRoot.route
    ) {
        composable(NavRoutes.AffiliateHome.route) {
            HomeAffiliatePage(navController)
        }
        composable(NavRoutes.Video.route) {
            VideoScreen(navController)
        }


        composable(NavRoutes.ProductByCategory.route) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""


            CategoryAffiliatePage(navController, categoryId)
        }


    }
}

