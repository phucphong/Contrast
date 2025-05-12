package com.contrast.Contrast.presentation.navigator.graph


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

import com.contrast.Contrast.presentation.components.profile.ProfileScreen
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.affiliate.home.HomePage
import com.contrast.Contrast.presentation.features.cart.ui.CartScreen
import com.contrast.Contrast.presentation.features.login.ui.LoginScreen
import com.contrast.Contrast.presentation.features.news.list.NewsScreen

import com.contrast.Contrast.presentation.features.notification.ui.NotificationScreen
import com.contrast.Contrast.presentation.features.video.VideoScreen
import com.contrast.Contrast.presentation.navigator.NavRoutes

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.affiliateNavGraph(navController: NavHostController,
                                      idProductFromShare: String= "0",
                                      idUnitFromShare: String= "0",
                                      introducerId: String= "0"
                                      ) {
    navigation(
        startDestination = NavRoutes.AffiliateHome.route, route = NavRoutes.AffiliateRoot.route
    ) {
        composable(NavRoutes.AffiliateHome.route) {
            HomePage(navController)
        }
        composable(NavRoutes.Login.route) {
            LoginScreen(navController)
        }


        composable(NavRoutes.Category.route) {
            CategoryAffiliatePage(navController,"0")
        }

        composable(NavRoutes.Videos.route) {
            VideoScreen(navController)
        }

        composable(NavRoutes.News.route) {
            NewsScreen(navController)
        }



        composable(
            route = NavRoutes.Notifications.route,
            arguments = NavRoutes.Notifications.arguments
        ) { backStackEntry ->
            val startDate = backStackEntry.arguments?.getString("startDate") ?: ""
            val endDate = backStackEntry.arguments?.getString("endDate") ?: ""
            NotificationScreen(navController, startDate,endDate)
        }


        composable(NavRoutes.Account.route) {
            ProfileScreen(navController)
        }
        composable(NavRoutes.Carts.route) {
            CartScreen(navController)
        }
        productnavGraph(navController)

//        accountNavGraph(navController)




    }
}

