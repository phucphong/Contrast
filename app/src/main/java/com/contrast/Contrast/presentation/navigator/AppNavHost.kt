package com.contrast.Contrast.presentation.navigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.account.ui.AccountScreen
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.affiliate.home.HomeAffiliatePage
import com.contrast.Contrast.presentation.features.main.location.ui.LocationScreen
import com.contrast.Contrast.presentation.features.store.ui.StoreListScreen
import com.contrast.Contrast.presentation.features.membership.rewards.RewardsScreen
import com.contrast.Contrast.presentation.navigator.graph.accountNavGraph
import com.contrast.Contrast.presentation.navigator.graph.affiliateNavGraph
import com.contrast.Contrast.presentation.navigator.graph.contrastNavGraph
import com.contrast.Contrast.presentation.features.video.VideoScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
<<<<<<< HEAD:app/src/main/java/com/contrast/Contrast/presentation/features/navigator/AppNavHost.kt
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeAffiliatePage(navController) }
        composable("location") { LocationScreen(navController) }
        composable("storeList") { StoreListScreen(navController) }
        composable("membership") { RewardsScreen(navController) }
        composable("account") { AccountScreen(navController) }
        composable("product/{categoryId}") {AccountScreen(navController)  }
=======
    NavHost(navController, startDestination = NavRoutes.AffiliateRoot.route) {
        affiliateNavGraph(navController)
        contrastNavGraph(navController)
        accountNavGraph(navController)
>>>>>>> 690e998dc9202a76c93e2573871ef4fedb64786a:app/src/main/java/com/contrast/Contrast/presentation/navigator/AppNavHost.kt
    }
}
