package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.contrast.Contrast.presentation.features.domain.DomainScreen
import com.contrast.Contrast.presentation.features.forgotPassword.ForgotPasswordScreen
import com.contrast.Contrast.presentation.features.login.ui.LoginScreen
import com.contrast.Contrast.presentation.features.register.ui.info.RegisterAccountScreen
import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes


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
    registerMainRoutes(navController)


}