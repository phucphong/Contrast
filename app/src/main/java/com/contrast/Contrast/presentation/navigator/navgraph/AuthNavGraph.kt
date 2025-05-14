package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.affiliate.AffiliateMainScreen
import com.contrast.Contrast.presentation.features.affiliate.home.HomePage
import com.contrast.Contrast.presentation.features.domain.DomainScreen
import com.contrast.Contrast.presentation.features.forgotPassword.ForgotPasswordScreen
import com.contrast.Contrast.presentation.features.login.ui.LoginScreen
import com.contrast.Contrast.presentation.features.register.ui.info.RegisterAccountScreen
import com.contrast.Contrast.presentation.navigator.router.routes.AffiliateRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.AuthRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.MainRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerAuthRoutes(
    navController: NavHostController
) {



    composable(
        route = AuthRoutes.Login.route,
        arguments = AuthRoutes.Login.arguments
    ) { backStackEntry ->
        val isClose = backStackEntry.arguments?.getString("isClose") ?:"0"
        LoginScreen(navController,isClose)
    }
    composable(AuthRoutes.Register.route) {
        RegisterAccountScreen(navController)
    }

    composable(AuthRoutes.ForgotPassword.route) {
        ForgotPasswordScreen(navController)
    }

    composable(AuthRoutes.Domain.route) { DomainScreen(navController) }

    composable(
        route = MainRoutes.Main.route,
        arguments = MainRoutes.Main.arguments
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val idUnit = backStackEntry.arguments?.getString("idUnit") ?: ""
        val introducerId = backStackEntry.arguments?.getString("introducerId") ?: ""
        AffiliateMainScreen(navController, id, idUnit,introducerId)
    }

}