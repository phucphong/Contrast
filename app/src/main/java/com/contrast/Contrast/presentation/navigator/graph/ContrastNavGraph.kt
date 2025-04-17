package com.contrast.Contrast.presentation.navigator.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.contrast.Contrast.presentation.features.main.home.ui.HomePage
import com.contrast.Contrast.presentation.features.main.location.ui.LocationScreen
import com.contrast.Contrast.presentation.features.membership.rewards.RewardsScreen
import com.contrast.Contrast.presentation.features.store.ui.StoreListScreen
import com.contrast.Contrast.presentation.features.video.VideoScreen
import com.contrast.Contrast.presentation.navigator.NavRoutes

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.contrastNavGraph(navController: NavHostController) {
    navigation(startDestination = NavRoutes.Home.route, route = NavRoutes.MainRoot.route) {
        composable(NavRoutes.Home.route) { HomePage(navController) }
        composable(NavRoutes.Video.route) { VideoScreen(navController) }
        composable(NavRoutes.Location.route) { LocationScreen(navController) }
        composable(NavRoutes.StoreList.route) { StoreListScreen(navController) }
        composable(NavRoutes.Membership.route) { RewardsScreen(navController) }
    }
}
