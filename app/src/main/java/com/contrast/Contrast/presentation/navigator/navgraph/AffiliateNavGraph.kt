package com.contrast.Contrast.presentation.navigator.navgraph



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.contrast.Contrast.presentation.components.profile.ProfileScreen

import com.contrast.Contrast.presentation.features.affiliate.home.HomePage
import com.contrast.Contrast.presentation.features.news.list.NewsScreen
import com.contrast.Contrast.presentation.features.video.VideoScreen
import com.contrast.Contrast.presentation.navigator.routers.AccountRoutes
import com.contrast.Contrast.presentation.navigator.routers.AffiliateRoutes
import com.contrast.Contrast.presentation.navigator.routers.NewsRoutes
import com.contrast.Contrast.presentation.navigator.routers.VideoRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerAffiliateRoutes(
    navController: NavHostController
) {
    navigation(
        startDestination = AffiliateRoutes.AffiliateHome.route,
        route = AffiliateRoutes.AffiliateRoot.route
    ) {
        composable(AffiliateRoutes.AffiliateHome.route) {
            HomePage(navController)
        }

        registerCategoryProductRoutes(navController)
        composable(VideoRoutes.Videos.route) {
           VideoScreen(navController)
        }

        composable(NewsRoutes.NewsList.route) {
            NewsScreen(navController)
        }

        composable(AccountRoutes.Account.route) {
            ProfileScreen(navController)
        }
    }
}
