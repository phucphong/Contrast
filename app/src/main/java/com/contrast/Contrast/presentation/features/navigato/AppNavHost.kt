package com.contrast.Contrast.presentation.features.navigato

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.presentation.features.account.personalInfo.PersonalInfoScreen
import com.contrast.Contrast.presentation.features.account.ui.AccountScreen
import com.contrast.Contrast.presentation.features.main.home.ui.HomePage
import com.contrast.Contrast.presentation.features.main.location.ui.LocationScreen
import com.contrast.Contrast.presentation.features.main.store.ui.StoreListScreen
import com.contrast.Contrast.presentation.features.membership.rewards.RewardsScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomePage(navController) }
        composable("location") { LocationScreen(navController) }
        composable("storeList") { StoreListScreen(navController) }
        composable("membership") { RewardsScreen(navController) }
        composable("account") { AccountScreen(navController) }
        composable("personal_info") { PersonalInfoScreen(navController) } // Thêm màn hình này
    }
}
