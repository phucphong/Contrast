package com.contrast.Contrast.presentation.features.navigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.account.personalInfo.PersonalInfoScreen
import com.contrast.Contrast.presentation.features.account.ui.AccountScreen
import com.contrast.Contrast.presentation.features.affiliate.home.HomeAffiliatePage
import com.contrast.Contrast.presentation.features.main.home.ui.HomePage
import com.contrast.Contrast.presentation.features.main.location.ui.LocationScreen
import com.contrast.Contrast.presentation.features.store.ui.StoreListScreen
import com.contrast.Contrast.presentation.features.membership.rewards.RewardsScreen
import com.contrast.Contrast.presentation.features.product.ProductDetailScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeAffiliatePage(navController) }
        composable("location") { LocationScreen(navController) }
        composable("storeList") { StoreListScreen(navController) }
        composable("membership") { RewardsScreen(navController) }
        composable("account") { AccountScreen(navController) }
        composable("product/{categoryId}") {AccountScreen(navController)  }
    }
}
//