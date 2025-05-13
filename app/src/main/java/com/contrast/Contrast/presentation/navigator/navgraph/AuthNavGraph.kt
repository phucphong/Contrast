package com.contrast.Contrast.presentation.navigator.navgraph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.forgotPassword.ForgotPasswordScreen
import com.contrast.Contrast.presentation.features.login.ui.LoginScreen
import com.contrast.Contrast.presentation.features.register.ui.info.RegisterAccountScreen
import com.contrast.Contrast.presentation.navigator.router.routes.AuthRoutes


fun NavGraphBuilder.registerAuthRoutes(navController: NavHostController
) {


    composable(AuthRoutes.ForgotPassword.route) { /* ForgotPasswordScreen() */ }
    composable(AuthRoutes.Domain.route) { /* DomainScreen() */ }
    composable(AuthRoutes.Logout.route) { /* LogoutScreen() */ }


    composable(AuthRoutes.Login.route) {
        LoginScreen(navController)
    }
    composable(AuthRoutes.Register.route) {
        RegisterAccountScreen(navController)
    }

composable(AuthRoutes.ForgotPassword.route) {
    ForgotPasswordScreen (navController)
    }
}