package com.contrast.Contrast.presentation.navigator.graph
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.contrast.Contrast.presentation.features.account.personalInfo.PersonalInfoScreen
import com.contrast.Contrast.presentation.features.account.ui.AccountScreen
import com.contrast.Contrast.presentation.navigator.router.NavRoutes

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.accountNavGraph(navController: NavHostController) {
    navigation(
        startDestination = NavRoutes.Account.route,
        route = NavRoutes.AccountRoot.route
    ) {
        composable(NavRoutes.Account.route) {
            AccountScreen(navController)
        }
        composable(NavRoutes.PersonalInfo.route) {
            PersonalInfoScreen(navController)
        }
    }
}
