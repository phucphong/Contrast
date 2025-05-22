package com.contrast.Contrast.presentation.navigator.navgraph


import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.contrast.Contrast.presentation.features.domain.DomainScreen

import com.contrast.Contrast.presentation.features.login.ui.LoginScreen


import com.contrast.Contrast.presentation.features.splas.SplashScreen

import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController, startIntent: Intent?) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen( navController,startIntent)
        }

        composable(
            route = AuthRoutes.Login.route,
            arguments = AuthRoutes.Login.arguments
        ) { backStackEntry ->
            val isClose = backStackEntry.arguments?.getString("isClose") ?:"0"
            LoginScreen(navController,isClose)
        }
        composable(AuthRoutes.Domain.route) { DomainScreen(navController) }
        registerMainRoutes(navController)





    }
}
